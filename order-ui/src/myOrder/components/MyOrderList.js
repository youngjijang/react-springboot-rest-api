import React from 'react';
import {Product} from "../../components/Product";

export function MyOrderList({orders = []}) {
    return (
        <React.Fragment>
            <ul className="list-group products">
                {orders.map(v =>
                    <li key={v.orderId} className="list-group-item d-flex mt-3">
                        <Product {...v}/>
                    </li>
                )}
            </ul>
        </React.Fragment>
    );
}


