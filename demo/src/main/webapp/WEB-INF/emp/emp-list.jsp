
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
        #index{
            text-decoration: none;
            color: black;
            padding: 3px;
            margin: 3px;
        }
        #index {
            text-decoration: none;
        }
        #index .active{
            font-weight: bold;
            text-decoration: underline;
        }
        a{
            text-decoration: none;
        }
    </style>
</head>
<body>
    <div id="app">
        <!-- html 코드는 id가 app인 태그 안에서 작업 -->
         <div>
            <select v-model="pageSize" @change=" currentPage =1 ; fnList();">
                <option value="5">5개씩</option>
                <option value="10">10개씩</option>
                <option value="20">20개씩</option>
            </select>
         </div>
         <table>
            <tr>
                <th>사번</th>
                <th>이름</th>
                <th>직급</th>
                <th>사수</th>
                <th>급여 등급</th>
                <th>부서</th>
            </tr>
            <tr v-for="item in list">
                <td>{{item.empNo}}</td>
                <td><a href="javascript:;" @click="fnView(item.empNo)">{{item.eName}}</a></td>
                <td>{{item.job}}</td>
                <td>{{item.sasu}}</td>
                <td>{{item.grade}}</td>
                <td>{{item.dName}}</td>
            </tr>
         </table>
         <div>
            <a href="javascript:;" @click="currentPage -= 1; fnList();" v-if="currentPage != 1">◀</a>
            <a @click="fnPage(num)" id="index" href="javascript:;" v-for="num in index" >
                <span :class="{active : currentPage == num}">{{num}}</span>
            </a>
            <a href="javascript:;" @click="currentPage += 1; fnList();" v-if="currentPage != index">▶</a>
         </div>
         <a href="/emp-add.do"><button>글쓰기</button></a>
    </div>
</body>
</html>

<script>
    const app = Vue.createApp({
        data() {
            return {
                // 변수 - (key : value)
                list : [],
                empNo : "",
                pageSize : 5, // 한 페이지에 출력할 개수
                index : 1, // 최대 페이지 수
                currentPage : 1, // 현재 페이지
            };
        },
        methods: {
            // 함수(메소드) - (key : function())
            fnList: function () {
                let self = this;
                let param = {
                    pageSize : self.pageSize,
                    offSet : self.pageSize * (self.currentPage - 1) // db에서 건너 뛸 개수
                }
                    ;
                $.ajax({
                    url: "http://localhost:8080/emp/list.dox",
                    dataType: "json",
                    type: "POST",
                    data: param,
                    success: function (data) {
                        console.log(data);
                        self.list= data.list;
                        // 최대 페이지 수 구하는 식
                        self.index = Math.ceil(data.totalCount /* 전체 개수 */ / self.pageSize );
                        console.log(self.index);
                    }
                });
            },
            fnView : function(empNo){
                // alert(boardNo);
                pageChange("/emp-view.do" ,{empNo : empNo});
            },
            fnPage : function(page){
                let self = this;
                self.currentPage = page;
                self.fnList();
            }
        }, // methods
        mounted() {
            // 처음 시작할 때 실행되는 부분
            let self = this;
            self.fnList();
        }
    });

    app.mount('#app');
</script>