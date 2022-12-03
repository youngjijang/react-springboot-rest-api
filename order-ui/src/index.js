import React from 'react';
import './index.css';
import App from './App';
import { render } from "react-dom";
import {
    BrowserRouter,
    Routes,
    Route,
} from "react-router-dom";
import MyOrders from './myOrder/MyOrders';
import reportWebVitals from './reportWebVitals';

const rootElement = document.getElementById("root");
render(
    <BrowserRouter>
        <Routes>
            <Route path="/" element={<App />} />
            <Route path="myOrder" element={<MyOrders />} />
        </Routes>
    </BrowserRouter>,
    rootElement
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
