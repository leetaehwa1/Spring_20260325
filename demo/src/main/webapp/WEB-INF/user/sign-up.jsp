 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Document</title>
        <script src="https://code.jquery.com/jquery-3.7.1.js"
            integrity="sha256-eKhayi8LEQwp4NKxN+CfCh+3qOVUtJn3QNZ0TciWLP4=" crossorigin="anonymous"></script>
        <script src="https://unpkg.com/vue@3/dist/vue.global.js"></script>
        <style>
            body {
                background-color: #f4f6f9;
                font-family: Arial, sans-serif;
            }

            #container {
                width: 400px;
                margin: 100px auto;
                padding: 30px;
                background: white;
                border-radius: 10px;
                box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            }

            h2 {
                text-align: center;
                margin-bottom: 20px;
            }

            .form-group1 {
                margin-bottom: 15px;
            }

            .form-group2 {
                margin-bottom: 15px;
                margin-right: 20px;
            }

            label {
                display: block;
                margin-bottom: 5px;
                font-weight: bold;
            }

            input {
                width: 100%;
                padding: 10px;
                border: 1px solid #ccc;
                border-radius: 5px;
            }

            .id-box {
                display: flex;
                gap: 10px;
            }

            .id-box input {
                flex: 1;
            }

            button {
                padding: 10px;
                border: none;
                border-radius: 5px;
                cursor: pointer;
            }

            .btn-check {
                background-color: #6c757d;
                color: white;
            }

            .btn-join {
                width: 100%;
                background-color: #007bff;
                color: white;
                margin-top: 10px;
                font-size: 16px;
            }

            .btn-join:hover {
                background-color: #0056b3;
            }
        </style>
    </head>

    <body>
        <div id="container">
            <div id="app">

                <h2>회원가입</h2>

                <div class="form-group1">
                    <label>아이디
                        <div class="id-box">
                            <input v-model="userId" placeholder="아이디 입력">
                            <button @click="fnCheck()" class="btn-check">중복</button>
                        </div>
                    </label>
                </div>

                <div class="form-group2">
                    <label>비밀번호
                        <input type="password" v-model="pwd" placeholder="비밀번호 입력">
                    </label>
                </div>

                <div class="form-group2">
                    <label>이름
                        <input v-model="userName" placeholder="이름 입력">
                    </label>
                </div>
                <div>
                    프로필이미지 <input type="file" id="file1" name="file1">
                </div>

                <div class="form-group1">
                    <label>주소
                        <div class="id-box">
                            <input v-model="addr" placeholder="주소 입력">
                            <button @click="fnAddr()" class="btn-check">주소검색</button>
                        </div>
                    </label>
                </div>

                <button @click="fnJoin()" class="btn-join">회원가입</button>

            </div>
        </div>
    </body>

    </html>

    <script>
        function jusoCallBack(roadFullAddr, roadAddrPart1, addrDetail, roadAddrPart2, engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn, detBdNmList, bdNm, bdKdcd, siNm, sggNm, emdNm, liNm, rn, udrtYn, buldMnnm, buldSlno, mtYn, lnbrMnnm, lnbrSlno, emdNo) {
            window.vueObj.addr = roadFullAddr;
        }

        const app = Vue.createApp({
            data() {
                return {
                    // 변수 - (key : value)
                    userId: "",
                    pwd: "",
                    userName: "",
                    addr: "",
                };
            },
            methods: {
                // 함수(메소드) - (key : function())
                fnCheck: function () {
                    let self = this;
                    let param = {
                        userId: self.userId
                    };
                    $.ajax({
                        url: "http://localhost:8080/check.dox",
                        dataType: "json",
                        type: "POST",
                        data: param,
                        success: function (data) {
                            alert(data.message);
                        }
                    });
                },
                fnJoin: function () {
                    let self = this;
                    
                    let param = {
                        userId: self.userId,
                        pwd: self.pwd,
                        userName: self.userName,
                    };
                    $.ajax({
                        url: "http://localhost:8080/join.dox",
                        dataType: "json",
                        type: "POST",
                        data: param,
                        success: function (data) {
                            alert(data.message);
                        }
                    });
                },
                
                fnAddr: function () {
                    window.open("/addr.do", "addr", "width=500, height=500");
                },
            },
                 // methods
            mounted() {
                // 처음 시작할 때 실행되는 부분
                let self = this;
                window.vueObj = this;
            }
        });

        app.mount('#app');
    </script> 

  