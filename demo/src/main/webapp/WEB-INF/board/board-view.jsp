
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script src="https://code.jquery.com/jquery-3.7.1.js" integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
    <script src="https://unpkg.com/vue@3/dist/vue.global.js"></script>
    <script src="/js/page-change.js"></script>

    <style>
        body{
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f5f6fa;
        }
        table{
            width: 500px;
            border-collapse: collapse;
            background: white;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }

       th{
            width: 120px;
            background-color: #989ba9;
            text-align: left;
        }
        td{ 
            text-align: left;
        }
        tr:nth-child(even){
            background-color:#fafafa;
        }
    </style>
</head>
<body>
    <div id="app">
        <!-- html 코드는 id가 app인 태그 안에서 작업 -->
         <table>
            <tr>
                <th>제목</th>
                <td>{{info.title}}</td>
                <th>조회수</th>
                <td>{{info.cnt}}</td>
            </tr>
            <div v-for="item in fileList">
                <img :src="item.filePath">
            </div>
            <tr>
                <th></th>
            </tr>
            <tr>
                <th>내용</th>
                <td colspan="4">{{info.contents}}</td>  
            </tr>
            <div>
                내용 (html태그적용) : <div v-html="info.contents"></div>
            </div>
         </table>
         <div v-if="info.userId == sessionId || sessionRole == 'A'">
            <button @click="fnEdit()">수정</button>
            <button>삭제</button>
         </div>
    </div>
</body>
</html>

<script>
    const app = Vue.createApp({
        data() {
            return {
                // 변수 - (key : value)
                boardNo : "${boardNo}",
                sessionId : "${sessionId}",
                sessionRole : "${sessionRole}",
                info :{
                },
                fileList :[],
            };
        },
        methods: {
            // 함수(메소드) - (key : function())
            fnGetBoard: function () {
                let self = this;
                let param = {
                    boardNo : self.boardNo,
                    kind : "view",
                };
                $.ajax({
                    url: "http://localhost:8080/board/board-info.dox",
                    dataType: "json",
                    type: "POST",
                    data: param,
                    success: function (data) {
                        console.log(data);  
                        self.info = data.info;
                        self.fileList = data.fileList;
                    }
                });
            },
            fnEdit : function(){
                let self = this;
                 pageChange("/board/board-edit.do" ,{boardNo : self.boardNo});
                
            }
        }, // methods
        mounted() {
            // 처음 시작할 때 실행되는 부분
            let self = this;
            self.fnGetBoard();

        }
    });

    app.mount('#app');
</script>