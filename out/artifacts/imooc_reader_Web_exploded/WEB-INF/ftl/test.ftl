<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <#--引入wangEditor编辑器-->
    <script src="/resources/wangEditor.min.js"></script>
</head>
<body>
    <div>
        <button id="btnRead">读取内容</button>
        <button id="btnWrite">写入内容</button>
    </div>
    <div id="divEditor" style="width: 800px; height:600px"></div>
    <script>
       var E=window.wangEditor;//获得wangEditor对象
       var editor=new E("#divEditor");//通过id选择器找到容器并完成富文本编辑器的初始化
       editor.create();//创建富文本编辑器，显示在页面上
       document.getElementById("btnRead").onclick=function (){
           var content=editor.txt.html();//获取编辑器现有的html内容
           alert(content);
       }
       document.getElementById("btnWrite").onclick=function (){//为富文本编辑器赋值
           var content="<li style='color: red'>我是<b>新内容</b></li>";
           editor.txt.html(content);//设置内容
       }
    </script>

</body>
</html>