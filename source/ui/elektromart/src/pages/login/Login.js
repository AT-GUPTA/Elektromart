import React from "react";
import "./Login.css";
import Form from "./Form";

const Login = ({authentication, setRoleId}) => {
    return (
        <div>
            <div className="login-box container text-bg-light rounded-5">
                <h3 className="text-center pt-2 mt-2 fw-semibold">Login</h3>
                <hr/>
                <Form authentication={authentication} setRoleId={setRoleId}/>
            </div>
        </div>
    );
};

export default Login;
