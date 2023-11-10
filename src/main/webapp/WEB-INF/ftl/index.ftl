<!DOCTYPE html>
<html lang="en"><head>
    <meta charset="UTF-8">
    <meta name="referrer" content="no-referrer" />
    <title>慕课书评网</title>
    <meta name="viewport" content="width=device-width,initial-scale=1.0, maximum-scale=1.0,user-scalable=no">
    <link rel="stylesheet" href="./resources/bootstrap/bootstrap.css">
    <link rel="stylesheet" href="./resources/raty/lib/jquery.raty.css">
    <script src="./resources/jquery.3.3.1.min.js"></script>
    <script src="./resources/bootstrap/bootstrap.min.js"></script>
    <#--模板插件-->
    <script src="./resources/art-template.js"></script>
    <#--星形评分插件-->
    <script src="./resources/raty/lib/jquery.raty.js"></script>
	
    <style>
        .highlight {
            color: red !important;
        }
        a:active{
            text-decoration: none!important;
        }
    </style>
    

    <style>
        .container {
            padding: 0px;
            margin: 0px;
        }

        .row {
            padding: 0px;
            margin: 0px;
        }

        .col- * {
            padding: 0px;
        }
    </style>
    <#--定义模板-->
    <script type="text/html" id="tpl"><#--type说明script标签中的内容类型为html-->
        <a href="/book/{{bookId}}" style="color: inherit">
            <div class="row mt-2 book">
                <div class="col-4 mb-2 pr-2">
                    <img class="img-fluid" src="{{cover}}">
                </div>
                <div class="col-8  mb-2 pl-0">
                    <h5 class="text-truncate">{{bookName}}</h5>
                    <div class="mb-2 bg-light small  p-2 w-100 text-truncate">{{author}}</div>
                    <div class="mb-2 w-100">{{subTitle}}</div>
                    <p>
                        <#--data-score为固定属性，通过该属性描述组件当前的评分-->
                        <span class="stars" data-score="{{evaluationScore}}" title="gorgeous"></span>
                        <span class="mt-2 ml-2">{{evaluationScore}}</span>
                        <span class="mt-2 ml-2">{{evaluationQuantity}}人已评</span>
                    </p>
                </div>
            </div>
        </a>
        <hr>
    </script>
<script>
    $.fn.raty.defaults.path="./resources/raty/lib/images"/*设置星星图片的存放路径*/

    //loadMore()加载更多数据
    //isReset参数设置为true，代表从第一位开始查询，否则按nextPage查询后续页
    function loadMore(isReset){//新增标志位对第一页进行加载
        if(isReset==true){//如果是初始加载时，将nextPage的值设为1
            $("#bookList").html("");//将原有的图书信息清空
            $("#nextPage").val(1);
        }
        //以下代码对于初始化和加载更多的情况都能使用
        var nextPage=$("#nextPage").val();/*获得下一页的值*/
        var categoryId=$("#categoryId").val();//获取分类编号
        var order=$("#order").val();//获取排序方式
        $.ajax({
            url:"/books",
            data:{categoryId:categoryId,order:order,p:nextPage},/*传入的数据名称要和函数的一样*/
            type:"get",
            dataType:"json",
            success:function (json){
                console.log(json);
                var list=json.records;//records要和服务器返回的属性名保持一致
                for(var i=0;i<list.length;i++){
                    var book=json.records[i];//循环提取列表中每一个图书的信息
                    // var html="<li>"+book.bookName+"</li>";
                    //将数据结合模板生成新的html
                    var html=template("tpl",book);
                    console.log(html);
                    $("#bookList").append(html);//向div追加一个html片段
                }
                /*选中所有类名为stars的标签，将其显示为星形*/
                $(".stars").raty({readOnly:true});//显示星形评价组件
                /*current代表当前页号*/
                //判断是否为最后一页
                if(json.current<json.pages){//如果当前页小于总页数
                    $("#nextPage").val(parseInt(json.current)+1);//更新下一页的值
                    $("#btnMore").show();//显示"加载更多"的按钮
                    $("#divNoMore").hide();//隐藏"没有更多"
                }else{//后面没有其他数据时
                    $("#btnMore").hide();//隐藏"加载更多"的按钮
                    $("#divNoMore").show();//显示"没有更多"
                }
            }
        })
    }
    $(function (){
        // $.ajax({
        //     url:"/books",
        //     data:{p:1},/*传入的数据名称要和函数的一样*/
        //     type:"get",
        //     dataType:"json",
        //     success:function (json){
        //         console.log(json);
        //         var list=json.records;//records要和服务器返回的属性名保持一致
        //         for(var i=0;i<list.length;i++){
        //             var book=json.records[i];//循环提取列表中每一个图书的信息
        //             // var html="<li>"+book.bookName+"</li>";
        //             //将数据结合模板生成新的html
        //             var html=template("tpl",book);
        //             console.log(html);
        //             $("#bookList").append(html);//向div追加一个html片段
        //         }
        //         /*选中所有类名为stars的标签，将其显示为星形*/
        //         $(".stars").raty({readOnly:true});//显示星形评价组件
        //     }
        // })
        loadMore(true);
    })

    /*绑定加载更多按钮单击事件*/
    $(function (){
        document.getElementById("btnMore").onclick=function (){
            loadMore(false);
        }
        $(".category").click(function (){/*分类名*/
            $(".category").removeClass("highlight");//移除所有category类的超链接高亮设置
            $(".category").addClass("text-black-50");//将所有分类超链接的颜色设置为灰色
            $(this).addClass("highlight");//将当前点击的对象设置为高亮显示
            var categoryId=$(this).data("category");//对应于span标签中data-category中的category值
            $("#categoryId").val(categoryId);//将这个值设置到对应的隐藏域中
            loadMore(true);//每点击一个分类，都是从第一页开始查询
        })
        $(".order").click(function (){/*排序条件*/
            $(".order").removeClass("highlight");//移除所有order类的超链接高亮设置
            $(".order").addClass("text-black-50");//将所有排序超链接的颜色设置为灰色
            $(this).addClass("highlight");//将当前点击的对象设置为高亮显示
            var order=$(this).data("order");//提取当前span中的data-score数据
            $("#order").val(order);
            loadMore(true);
        })

    })
</script>
</head>
<body>
<div class="container">
    <nav class="navbar navbar-light bg-white shadow mr-auto">
        <ul class="nav">
            <li class="nav-item">
                <a href="/">
                    <img src="https://m.imooc.com/static/wap/static/common/img/logo2.png" class="mt-1" style="width: 100px">
                </a>
            </li>

        </ul>
        <#if loginMember??><#--如果loginMember存在的话-->
             <h6 class="mt-1">
                <img style="width: 2rem;margin-top: -5px" class="mr-1" src="./images/user_icon.png">${loginMember.nickname}
             </h6>
            <#else>
                <a href="/login.html" class="btn btn-light btn-sm">
                    <img style="width: 2rem;margin-top: -5px" class="mr-1" src="./images/user_icon.png">登录
                </a>
        </#if>

    </nav>
    <div class="row mt-2">


        <div class="col-8 mt-2">
            <h4>热评好书推荐</h4>
        </div>

        <div class="col-8 mt-2">
                <span data-category="-1" style="cursor: pointer" class="highlight  font-weight-bold category">全部</span>
                |
                <#list categoryList as category>
                    <a style="cursor: pointer" data-category="${category.categoryId}"
                       class="text-black-50 font-weight-bold category">${category.categoryName}</a>
                    <#--判断后面是否还有数据，有的话执行if块中的语句-->
                    <#if category_has_next>|</#if>
                </#list>
        </div>

        <div class="col-8 mt-2">
            <span data-order="quantity" style="cursor: pointer" class="order highlight  font-weight-bold mr-3">按热度</span>

            <span data-order="score" style="cursor: pointer" class="order text-black-50 mr-3 font-weight-bold">按评分</span>
        </div>
    </div>
    <div class="d-none">
        <input type="hidden" id="nextPage" value="2">
        <input type="hidden" id="categoryId" value="-1">
        <input type="hidden" id="order" value="quantity">
    </div>

    <div id="bookList">
    

    </div>
    <button type="button" id="btnMore" data-next-page="1" class="mb-5 btn btn-outline-primary btn-lg btn-block">
        点击加载更多...
    </button>
    <div id="divNoMore" class="text-center text-black-50 mb-5" style="display: none;">没有其他数据了</div>
</div>

</body></html>