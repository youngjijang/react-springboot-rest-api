import React from 'react';

export function MyOrder(props) {
    const productId = props.orderId;
    const address = props.address;
    const orderStatus = props.orderStatus;
    const createdAt = props.createdAt;

    const handleAddBtnClicked = e => {
        props.onAddClick(productId);
    };

    return <React.Fragment>
        <div className="col">
            <div className="row text-muted">{address}</div>
            <div className="row">{createdAt}</div>
        </div>
        <div className="col text-center price">{orderStatus}</div>
        <div className="col text-end action">
            <button onClick={handleAddBtnClicked} className="btn btn-small btn-outline-dark">상세보기</button>
        </div>
    </React.Fragment>
}