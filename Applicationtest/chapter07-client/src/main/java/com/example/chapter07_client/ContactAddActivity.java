package com.example.chapter07_client;

import android.annotation.SuppressLint;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.chapter07_client.entity.Contact;

import java.util.ArrayList;

public class ContactAddActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_contact_name;
    private EditText et_contact_phone;
    private EditText et_contact_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_add);

        et_contact_name = findViewById(R.id.et_name);
        et_contact_phone = findViewById(R.id.et_age);
        et_contact_email = findViewById(R.id.et_height);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_query).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_save:
                //创建联系人对象
                Contact contact = new Contact();
                contact.name = et_contact_name.getText().toString().trim();
                contact.phone = et_contact_phone.getText().toString().trim();
                contact.email = et_contact_email.getText().toString().trim();

//                方式一，使用ContentResolver多次写入，每次一个字段
//                addContacts(getContentResolver(), contact);
//                方式二，批处理方式
                // 构建集合，一次性执行
                try {
                    addFullContacts(getContentResolver(), contact);
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (OperationApplicationException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_query:
                // 读取联系人
                readPhoneContacts(getContentResolver());
                break;
        }
    }

    private void readPhoneContacts(ContentResolver contentResolver) {
//       先查raw_contacts表，再根据raw_contacts_id去查询data表
        Cursor cursor = contentResolver.query(ContactsContract.RawContacts.CONTENT_URI, new String[]{ContactsContract.RawContacts._ID}, null, null);
        while (cursor.moveToNext()) {
            int rawContactId = cursor.getInt(0);
            Log.d("wcy", "readPhoneContacts rawContactId: "+rawContactId);
//           构建url
            Uri uri = Uri.parse("content://com.android.contacts/contacts/" + rawContactId + "/data");
            Cursor dataCursor = contentResolver.query(uri, new String[]{ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.Contacts.Data.DATA1, ContactsContract.Contacts.Data.DATA2}, null, null);
            Contact contact = new Contact();
            while (dataCursor.moveToNext()) {
                @SuppressLint("Range") String data1 = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Contacts.Data.DATA1));
                @SuppressLint("Range") String mineType = dataCursor.getString(dataCursor.getColumnIndex(ContactsContract.Contacts.Data.MIMETYPE));
                switch (mineType) {
//                    是姓名
                    case ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                        contact.name = data1;
                        break;
//                        是邮箱
                    case ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                        contact.email = data1;
                        break;
//                        是手机
                    case ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                        contact.phone = data1;
                        break;
                }

            }
            dataCursor.close();
//            ContactsContract.RawContacts表中出现的_id，不一定在Data表中都有对应记录，所以contact可能为空对象
            if (contact.name !=null) {
                Log.d("wcy",contact.toString());
            }
        }
        cursor.close();
    }

    private void addFullContacts(ContentResolver contentResolver, Contact contact) throws RemoteException, OperationApplicationException {
        // 创建一个加入联系人主记录的内容操作器
        ContentProviderOperation contentProviderOperation = ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build();

        // 创建一个插入联系人记录的内容操作器
        ContentProviderOperation contentProviderOperation1 = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                // 将第0个操作的id，作为data表的raw_contact_id
                .withValueBackReference(ContactsContract.Contacts.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.Contacts.Data.DATA2, contact.name)
                .build();

        // 创建一个插入联系人电话号码的内容操作器
        ContentProviderOperation contentProviderOperation2 = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                // 将第0个操作的id，作为data表的raw_contact_id
                .withValueBackReference(ContactsContract.Contacts.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.Contacts.Data.DATA1, contact.phone)
                .withValue(ContactsContract.Contacts.Data.DATA2, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build();

        // 创建一个插入联系人电子邮箱的内容操作器
        ContentProviderOperation contentProviderOperation3 = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                // 将第0个操作的id，作为data表的raw_contact_id
                .withValueBackReference(ContactsContract.Contacts.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.Contacts.Data.DATA1, contact.email)
                .withValue(ContactsContract.Contacts.Data.DATA2, ContactsContract.CommonDataKinds.Email.TYPE_WORK)
                .build();

        // 声明一个内容操作器的列表，并将上面四个操作器添加到该列表中
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        operations.add(contentProviderOperation);
        operations.add(contentProviderOperation1);
        operations.add(contentProviderOperation2);
        operations.add(contentProviderOperation3);

        // 批量提交
        contentResolver.applyBatch(ContactsContract.AUTHORITY, operations);

    }

    private void addContacts(ContentResolver contentResolver, Contact contact) {
        ContentValues values = new ContentValues();
//        向raw_contacts插入一条空记录，并得到添加的联系人id
        Uri uri = contentResolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(uri);

//        插入姓名流程
        ContentValues name = new ContentValues();
        // 关联联系人编号
        name.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
        // 设置姓名的数据类型
        name.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
//       设置姓名
        name.put(ContactsContract.Contacts.Data.DATA2, contact.name);
        // 向Data表插入记录
        contentResolver.insert(ContactsContract.Data.CONTENT_URI, name);

//        插入电话号码流程
        ContentValues phone = new ContentValues();
        phone.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
        // 类型是电话号码
        phone.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        phone.put(ContactsContract.Contacts.Data.DATA1, contact.phone);
//      电话号码的 联系人类型。 1家庭 2(TYPE_MOBILE)工作
        name.put(ContactsContract.Contacts.Data.DATA2, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
        contentResolver.insert(ContactsContract.Data.CONTENT_URI, phone);

        // 插入邮箱流程
        ContentValues email = new ContentValues();
        email.put(ContactsContract.Contacts.Data.RAW_CONTACT_ID, rawContactId);
        // 类型是邮箱
        email.put(ContactsContract.Contacts.Data.MIMETYPE, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        email.put(ContactsContract.Contacts.Data.DATA1, contact.email);
//      邮箱的 联系人类型。 1(TYPE_HOME)家庭 2(TYPE_WORK)工作
        email.put(ContactsContract.Contacts.Data.DATA2, ContactsContract.CommonDataKinds.Phone.TYPE_WORK);
        contentResolver.insert(ContactsContract.Data.CONTENT_URI, email);
    }
}