http://www.cnblogs.com/gbeniot/p/7294537.html
vue.js移动端app实战2
貌似有部分人要求写的更详细，这里多写一点vuel-cli基础的配置
什么是vue-cli？
官方的解释是：A simple CLI for scaffolding Vue.js projects，
简单翻译一下，就是： 用简单的命令行来生成vue.js项目脚手架。

<!-- 全局安装vue-cli -->

npm install -g vue-cli
vue-cli预先定义了5个模板，根据你使用的打包工具的不同选择不同的模板，通常我们用的都是第一个webpack模板。每个模板都预先写好了很多依赖和基础配置，可以直接在此基础上进行开发，非常方便。

webpack

webpack-simple

browserify

browserify-simple

simple

安装vue-cli后,就可以下载我们要的模板了。

用法：vue init template-name project-name


这时会有很多提示，询问你要安装vue2还是vue1，是否要安装mocha，eslint等东西，根据你自己的需要安装即可。安装好后，会提示你怎么开始，根据提示输入命令就可以启动了。



为了适配各种屏幕，首先把淘宝的flexible引进来，在main.js里面

import './base/js/base.js'
其次，把样式重置也引入进来, App.vue的style标签里面

@import './base/css/normalize.scss';
这里我有一个问题没有解决，就是开发中我用scss来写mixin，由于很多页面都会用到，所以我希望在App.vue里面引入mixin.scss，好让所以的vue组件都能用，但实际上这样并不起作用；后来又尝试写到main.js里面，不过也没用。目前只能是哪个组件需要用到mixin,就import进来，不过确实有点麻烦。
接着，引入字体图标, 在App.vue的style标签里面

@import url('//at.alicdn.com/t/font_nfzwlroyg2vuz0k9.css')
基本配置完成后，接下来分析一下路由怎么写：
为了达到上图的效果，我们需要2个基本的组件，一个是购物车，一个是home页面。购物车比较简单，就一个页面，主要看Home页面。

home组件又分成4个组件，一个是底部的导航，还有三个是上面的首页，搜索和个人中心。
也即是说为了达到图片上的效果，目前我们需要总共6个组件。

分别是：

1. 购物车 
2. home

    2.1 首页
    2.2 搜索
    2.3 个人中心
    2.4 底部导航
因此，新建6个.vue文件。为了尽快把路由编写出来，我喜欢随便填充一点内容（主要是为了知道在哪个页面），比如：

<div>首页首页首页</div>
<div>首页首页首页</div>
<div>首页首页首页</div>
<div>首页首页首页</div>
<div>首页首页首页</div>
接着编写路由：
使用路由首先要引入Vue-router并use，并将配置好路由的vue-router实例挂载到new出来的Vue实例上，不过vue-cli已将帮我们配置好了，只需要在其基础上继续开发就行了。

找到编写路由的index.js文件：
首先引入6个组件：

import xxx from 'xxx/xxx'

import car from '@/components/car'
你可能经常看到@这样的东西，这其实是webpack配置的别名。打开build文件夹下面的webpack.base.conf.js。


你也可以自己再加别名，比如

alias: {
    ~': resolve('src/component')
}
当webpack在import或者require语句中遇到~时，就会将其解析为对应的路径。使用别名可以使得路径更为清晰，也可以减少一些重复的代码。

对比一下：

import car from '../../component/car.vue'
import car from '~/car.vue'
不过，使用别名的坏处就是，编辑器没法智能的提示文件所在路径了。

当页面多了以后，打包后的文件会变得很大，大于1M也是很正常的。因此，首屏打开也会变慢，毕竟一下子要加载以M为单位的js文件。想要减少文件的大小，可以把Vue等公共库提取到vendor,从而利用浏览器的缓存效果。同时，也可以让路由按需加载，当需要用到的时候，才去加载对应的组件，利用webpakc的异步加载可以解决：

const Car = r => require.ensure([], () => r(require('@/components/car')), 'car')
也可以像下面这么写：

const Car = resolve => require(['@/components/car'], resolve)
Vue2.3+的版本提供了更高级的异步组件写法，想了解的可以去官网看一下，这里用的还是旧的用法。



对着上面的结构图，路由的结构其实大概已经了解了

{
    path:'',
    redirect:"/home"
},  
{
    path:'/home',
    component:Main,
    children:[
        {
            path:'',
            redirect:"index"
        },
        {
            path:'index',
            component:Index
        },
        {
            path:'search',
            component:Search
        },             
        {
            path:'vip',
            component:Vip                
        }
    ]
},
{
    path:'/car',
    component:Car,
}
这里我们用了2个重定向，当路由为空时，会重定向到/home，而当home为空时，又会重定向到index，所以你只需要在浏览器输入http://localhost:8088 ,就会自动跳转到home下的首页

开始编写home组件：
可以发现home组件由上下2部分组成，底部是固定的导航，上面的部分是动态切换的页面。因此home组件的template写出来应该是这样的：



<template>
   <div>
       <router-view></router-view>
        <foot-nav></foot-nav>
   </div>
</template>

    

<script>
    import footNav from '../components/foot-nav.vue'
    export default {
       components:{
            footNav
       }
    }
</script>
foot导航组件相对来说也比较简单，无非就是一个固定在底部的列表，每个列表都写好了对应的路由，点击每一个就会切换对应的页面。如果路由层级比较深，写起来可能会很长，如to="test1/test2/test3" ，考虑在配置路由的js中，给每个路由添加name。这样，在router-link中就只需要传递对应的name就可以了。

<template>
    <div class="foot-nav-containner">
        <ul class="bottom-nav">
            <router-link tag="li" :to='{name:"index"}' class="bottom-nav__li iconfont icon-shouye bottom-nav__li--home"></router-link>
            <router-link tag="li" :to='{name:"search"}' class="bottom-nav__li iconfont icon-ss bottom-nav__li--search"></router-link>
            <router-link tag="li" :to='{name:"car"}' class="bottom-nav__li iconfont icon-shoppingcart bottom-nav__li--car"></router-link>
            <router-link tag="li" :to='{name:"vip"}' class="bottom-nav__li iconfont icon-gerenzhongxinxia bottom-nav__li--vip"></router-link>
        </ul>
     </div>
</template>
index组件

index组件由轮播图以及三个排行榜组成。3个排行榜除了数据和名字不同个以外，其他的都一样。所以，我们总共需要2个组件就可以。大致如下：

<template>
    <div id="container">      
        <轮播图></轮播图>
        <排行榜 :类型=1></排行榜>
        <排行榜 :类型=2></排行榜>
        <排行榜 :类型=3></排行榜>  
    </div>
</template>
先来看轮播图:
轮播图我们用的是vue-awesome-swiper插件，使用方式同swiper基本一致，更多信息请github搜索。

在main.js中引入插件并使用：

import VueAwesomeSwiper from 'vue-awesome-swiper'

Vue.use(VueAwesomeSwiper);
由于可能不止一个页面会用到轮播图，所以我们可以把轮播图提取出来。

新建一个swiper.vue文件
<template>
    <swiper  class="swiper-box">
        <swiper-slide class="swiper-item"></swiper-slide>
        <swiper-slide class="swiper-item"></swiper-slide>
        <swiper-slide class="swiper-item"></swiper-slide>
        <swiper-slide class="swiper-item"></swiper-slide>
        <div class="swiper-pagination" slot="pagination"></div>
    </swiper>
</template>
<script>
export default {
    data(){
        return{
             swiperOption: {
                pagination: '.swiper-pagination',
                direction: 'horizontal',          
            } 
        }       
    },
     
};
</script>
<style lang="scss" scoped>
@import '../base/css/base.scss';
    .swiper-box {
        width: 100%;
        height: 100%;
        margin: 0 auto;
        .swiper-item {
            height: 5rem;
            background: url() no-repeat center/cover;
            /* 使用Mixin来处理2x,3x图 */
             &:nth-of-type(1){
             @include dpr-img("../assets/","vue"); 
           } 
            &:nth-of-type(2){
                @include dpr-img("../assets/","swiper1");
            }
            &:nth-of-type(3){
                @include dpr-img("../assets/","swiper2");
            }
            &:nth-of-type(4){
                @include dpr-img("../assets/","swiper3");
            }  
        }         }   
</style>
样式方面就忽略了，要作为一个组件，上面的写法还存在问题，主要体现在：

问题1：轮播图的配置参数写在组件data里面。
假如有2个页面需要用到这个组件，1个组件需要自动轮播，一个组价不需要自动轮播，这样的话，你可能会考虑对某个页面做单独处理，比如做一个if判断之类的。但是，假如有很多页面需要轮播图，而且不同的地方很多，比如你想对a页面轮播图滑动到下一张后alert(1),对b页面alert(2)等等等等，那该如何做呢？总不能一个一个判断吧，所以正确的方法应该是把配置参数通过prop接受父组件传递过来的参数

<script>
export default {
    data(){
        return{
            
        }       
    },
    props:{
        swiperOption:{
            type:Object
        }
    }
};
</script>
在父组件里面import组件并传递参数

<template>
    <div id="container">      
        <swiperComponent :swiperOption="swiperOption"></swiperComponent>       
    </div>
</template>

<script>
import swiperComponent from './swiper.vue'
export default {
    data() {
        return {
            swiperOption: {
                pagination: '.swiper-pagination',
                direction: 'horizontal',          
            },                      
        }
    },
    components:{
        swiperComponent,
    }    
}
</script>
如此一来，当哪个页面需要用到轮播图，就在哪个页面写好参数，并通过v-bind传递需要的参数。

问题2：轮播图数量固定。
不可能每个页面都是4个轮播图，而应该某个参数（一个数组）的长度来决定。父组件在通过ajax请求后获得该数组，并通过prop传递给swiper组件。

<template>
    <swiper  class="swiper-box">
        <swiper-slide class="swiper-item" v-for="(v,i) in swiperList "></swiper-slide>     
        <div class="swiper-pagination" slot="pagination"></div>
    </swiper>
</template>

props:{
     swiperList：{
        type:Array,
        default:[]
   }   
}
假如你是用的img标签，则 :src="v.img";

假如你是用background,则 :style="{backgroundImage:v.img}"
这样，我们的swiper组件基本已经解耦了。

排行榜
新建一个电影排行榜组film.vue文件
排行榜组件结构如下：
(样式基本人人会写，不再多说)

<template>
     <div class="film">
        <h3 class="film__type">
            <span>{{type}}</span>
            <router-link :to='{path:"/classify/"+url}'><span class="more"><em>更多</em><em class="iconfont icon-more"></em></span></router-link>
                          
        </h3>
        <div class="film__list" :ref="el" :data-request="url">         
            <ul class="clearfix">
                <router-link tag="li" v-for="(v,i) in array" :key="v.id" :to='{path:"/film-detail/"+v.id}'>
                    <div class="film__list__img"><img v-lazy="v.images.small" alt=""></div>
                    <div class="film__list__detail">
                        <h4 class="film__list__title">{{v.title}}</h4>
                        <p class="film__list__rank">评分：{{v.rating.average}}</p>
                        <p class="film__list__rank">
                            <span :class="{rankColor:v.rating.average>((i-0.5)*2)}" class="iconfont icon-rank" v-for="i in 5"></span>
                        </p>
                    </div>
                </router-link>    
            </ul>
             <Loading v-show="!array[0]" class="loading-center"></Loading>
        </div>
    </div>
</template>
为了获取真实的数据，我们需要：

豆瓣的api
很多页面都会用到豆瓣的api地址，所以可以把相同的部分提取到一个文件

找一个地方，新建文件api.js

const api="https://api.douban.com/v2/movie/";
export default api;
发送请求
选一个你熟悉的ajax库，这里用的是axios

在main.js里面引入axions库并use：

import axios from 'axios'
Vue.use(axios);
Vue.prototype.$ajax=axios;
我们把他挂载到vue的原型上，以便可以在所有地方通过this.$ajax上使用，不过你也可以不这么做，随个人喜欢。

解决豆瓣api跨域问题
如果你就这样发请求到豆瓣，是获取不到数据到，会提示你跨域，这也是前后端分离项目常见的问题。解决的方法有2个

1个是通过webpack的dev-server配置proxy, 不过有一个问题，就是只在开发阶段可以使用,也即是当你开发完成后，npm run build生成打包后的文件，想再去服务器看效果就不行了。

2 是我现在用的，通过设置chrome来跨域，具体设置方法请参考
这篇文章。设置完后，以后所以的项目都可以使用，无需对每个项目再单独配置proxy代理了。

搞定前提条件后，接下来的无非是在create或者mounted生命周期发送一个请求，请求成功后把数据赋值给v-for绑定的data了。不过还有问题，就是滚动条的问题。假如你不做任何处理，那么当获取数据成功后，会渲染20个li（根据后台返回的长度）。很明显，ul的长度肯定超过了整个屏幕的宽度，所以X轴会一直拉长，底下会出现滚动条，你可以拖动到屏幕之外。但是，我们希望的是屏幕的宽度保持不变，列表超过屏幕时隐藏元素，由我们手动去滑动。

那么ul的长度为多少了？假如你随便写一个很长的长度，那么滑动到后面就全是空白了。如果太短了，就滑动不了。因此ul长度由后台返回的数量*(li的宽带+li的padding-right，这里的加法取决于你的LI的css结构)决定。因此，获取完数据后，在nextTick时需要给UL的宽度重新赋值。为了方便获取元素的样式，可以在util.js写一个方法

export default function(el,style){
    return parseInt(window.getComputedStyle(el, false)[style])
}
在methods里写一方法计算ul应有的宽度，el即ul元素，可以通过绑定ref来获取。

freshWidth(el){
    var width=getStyle(el.children[0],"width");
    var padding=getStyle(el.children[0],"padding-right");
    el.style.width=el.children.length*(width+padding+2)+"px";              
}
为了能够拖动，有2种解决方式：

第一种比较简单，就是给Ul的外层div1设置固定长宽overflow:auto，当ul超出时，div就会出现滚动条，这样就以拖动了。不过会出现滚动条，很碍眼。因此，给div1外面再套一层div2，并设置div2的高度低于div1，比如最为层的div2高度80px,div1高度100px，并设置div2的overflow：hidden。如此一来，就可以隐藏滚动条，并且可以拖动了。



第二种是使用better-scroll库, 使用better-scroll需要2层的结构

<div id="containner">
    <ul id="scroller"></ul>
</div>
container层为初始化的元素，需要设置overflow:hidden;初始化后，第一个子元素ul就可滚动了。

引入better-scroll

1.在mounted生命周期阶段new BScroll({})并传参数初始化；
2.发送请求获取数据，
<!-- 初始化的元素以及请求的参数我们也都通过prop接受父组件传递过来 -->
3.将后台数据赋值给array数组
4.调用nextTick方法并在回调函数中重新计算ul的长度后，refresh scroller
<!-- 父组件 -->
 <filmComponent 
    :el="filmType.topFilmData.scroller" 
    :url="filmType.topFilmData.url" 
    :type="filmType.topFilmData.type">         
</filmComponent>
<script>
export default {
    data() {
        return {           
            filmType:{
                topFilmData:{
                    scroller:"scroll-top250",
                    url:"top250",
                    type:"top250"
                }                
            }
            
        }
    },
    components:{
        filmComponent
    } 
  }
</script>
<!-- 子组件 -->
   <script>
import BScroll from 'better-scroll'
import getStyle from '../base/js/util.js'
import Loading from './loading.vue'
import api from "../base/js/api.js"
export default {
    data () {
        return {
            scroller:null,<!-- 存放scroll元素 -->
            array:[]，<!-- 存放后台返回的数组 -->
        };
    },
    components:{
        Loading
    },
    props:["el","url","type"],
    mounted(){
        const el = this.$refs[this.el];
        this.scroller=this.initScroll(el);
        const {request}=el.dataset;

        this.$ajax.get(`${api}${request}?start=${Math.floor(Math.random()*10)}`)
            .then((res)=>{
                this.array=res.data.subjects;
                this.$nextTick(()=>{
                     this.freshWidth(el.children[0]); 
                     this.scroller.refresh();                   
                })             
            }) 
    
    },
    methods:{
        initScroll(el){
            return new BScroll(el,{
                click:true,
                probeType:3,
                scrollX:true,
                scrollY:false
            })
        },
        freshWidth(el){
            var width=getStyle(el.children[0],"width");
            var padding=getStyle(el.children[0],"padding-right");
            el.style.width=el.children.length*(width+padding+2)+"px";              
        },
    }
};
</script>
我们总共有3个排行榜，那么只需要在父组件再写2个标签并在data中写好参数，传给子组件就可以了

<filmComponent 
    :el="filmType.topFilmData.scroller" 
    :url="filmType.topFilmData.url" 
    :type="filmType.topFilmData.type">         
</filmComponent>

<filmComponent 
    :el="filmType.topFilmData.scroller" 
    :url="filmType.topFilmData.url" 
    :type="filmType.topFilmData.type">         
</filmComponent>
这样基本就就完成了，再稍微优化下，我们有3个榜单，假设每个榜单都加载20个数据，那么获取完数据后就会有3*20总共有60张图片的请求。常用的优化方式就是等图片进入可视区之后再去加载图片，在之前给一张loading或者其他图片作为站位。

使用vue-lazyload来达到这个效果，相关配置参数请自行github搜索vue-lazyload

在main.js里面

import VueLazyload from 'vue-lazyload'
Vue.use(VueLazyload, {
   preLoad: 1.3,
   loading: require('@/assets/head.jpg'),<!-- 站位图 -->
  attempt: 1
})
使用的方法非常简单:

原本我们是这么写的

<div><img :src="v.images.small" alt=""></div>
改成下面这样就行了

<img v-lazy="v.images.small" alt=""></div>
写到这里，基本配置以及首页就差不多完成了，相关代码已长传到 https://github.com/linrunzheng/vueApp

以上皆为本人学习过程中的一点回顾，如有错误还望指出。

 

 
 
