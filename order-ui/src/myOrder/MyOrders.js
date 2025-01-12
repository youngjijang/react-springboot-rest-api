import './MyOrder.css';
import React, {useState} from 'react';
import {MyOrderList} from './components/MyOrderList';
import axios from "axios";

function MyOrders(props) {

    const [email, setEmail] = useState();
    const [orders, setOrders] = useState([]);


    const handleEmailInputChanged = (e) => setEmail(e.target.value);
    const handleSubmit = (e) => {
        if (email === "") {
            alert("사용자 이메일을 입력해주세요!")
        } else {
            axios.get(`http://localhost:8080/api/v1/myOrder?address=${email}`).then(
                v => {
                    console.log(v.data);
                    if(v.data.length === 0){
                        alert("이메일에 해당 하는 주문이 없습니다.");
                        setEmail("");
                    }
                    setOrders(v.data);
                },
                e => {
                    alert("서버 장애");
                    console.error(e);
                })
        }
    }


    return (
        <div className="container-fluid">
            <div className="row justify-content-center m-4">
                <h1 className="text-center">Everything Of Cafe</h1>
            </div>
            <div className="card myOrder" id="change">
                <div className="row">
                    <div className="col-md-8-2 mt-4 d-flex flex-column align-items-start p-3 pt-0">
                        <h5 className="flex-grow-0"><b>주문 조회</b></h5>
                        <div className="row search">
                            <div className="col">
                                <input type="text" className="form-control mb-1" id="search_email" placeholder="Email"
                                       value={email} onChange={handleEmailInputChanged}/>
                            </div>
                            <div className="col">
                                <button type="submit" className="btn btn-dark" onClick={handleSubmit}>검색</button>
                            </div>
                        </div>
                        <MyOrderList orders={orders}/>
                    </div>
                </div>
            </div>
        </div>
    );
}

export default MyOrders;