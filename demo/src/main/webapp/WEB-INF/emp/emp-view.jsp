
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
        table, tr, td, th{
            border : 1px solid black;
            border-collapse: collapse;
            padding : 5px 10px;
            text-align: center;
        }
        th{
            background-color: beige;
        }
        tr:nth-child(even){
            background-color: azure;
        }
    </style>
</head>
<body>
    <div id="app">
        <!-- html 코드는 id가 app인 태그 안에서 작업 -->
        <table>
            <tr>
                <th>사번</th>
                <td>{{info.empNo}}</td>
                <th>이름</th>
                <td>{{info.eName}}</td>
                <th>직급</th>
                <td>{{info.job}}</td>
            </tr>
        </table>
        <div>
            <button @click="fnRemove(info.empNo)">삭제하기</button>
        </div>
    </div>
</body>
</html>

<script>
    const app = Vue.createApp({
        data() {
            return {
                // 변수 - (key : value)
                 empNo : "${empNo}",
                 info :{},

            };
        },
        methods: {
            // 함수(메소드) - (key : function())
            fnGetEmp: function () {
                let self = this;
                let param = {
                    empNo : self.empNo,
                };
                $.ajax({
                    url: "http://localhost:8080/emp/info.dox",
                    dataType: "json",
                    type: "POST",
                    data: param,
                    success: function (data) {
                        console.log(data);  
                        self.info = data.info;
                       
                    }
                });
            },
            fnRemove : function(empNo){
                if(!confirm("삭제하시겠습니까?")){
                    return;
                }

                let self = this;
                let param = {
                    empNo : empNo,
                };
                $.ajax({
                    url: "http://localhost:8080/emp/remove.dox",
                    dataType: "json",
                    type: "POST",
                    data: param,
                    success: function (data) {
                         alert(data.message);
                         location.href="/emp-list.do";
                    }
                });
            }
        }, // methods
        mounted() {
            // 처음 시작할 때 실행되는 부분
            let self = this;
            self.fnGetEmp();
        }
    });

    app.mount('#app');
</script>