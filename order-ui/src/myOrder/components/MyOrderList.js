import React from 'react';
import {MyOrder} from "./MyOrder";

export function MyOrderList({orders = []}) {
    return (
        <React.Fragment>
            <ul className="list-group products">
                {orders.map(v =>
                    <li key={v.orderId} className="list-group-item d-flex mt-3">
                        <MyOrder {...v}/>
                    </li>
                )}
            </ul>
        </React.Fragment>
    );
}


