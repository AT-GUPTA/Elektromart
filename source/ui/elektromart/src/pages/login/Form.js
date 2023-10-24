import React, {useState} from "react";
import "./Form.css";
import {Link, useNavigate} from "react-router-dom";
import Swal from "sweetalert2";

const Form = ({authentication, setRoleId}) => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const navigate = useNavigate();
    const [showPassword, setShowPassword] = useState(false);

    const togglePasswordVisibility = () => {
        setShowPassword(prevShowPassword => !prevShowPassword);
    };

    const passwordFieldType = showPassword ? "text" : "password";

    const loginVerification = async (user) => {
        const res = await fetch(`http://localhost:8080/Elektromart_war/user/login`, {
            method: "POST",
            mode: "cors",
            headers: {"Content-type": "application/json"},
            body: JSON.stringify(user),
        });

        const jsonResponse = await res.json();
        const status = jsonResponse["status"];
        const message = jsonResponse["message"];
        const isLoggedIn = status === "SUCCESS";
        const roleId = jsonResponse["roleId"];

        localStorage.setItem("cart_id", jsonResponse["cartId"]);

        if (isLoggedIn) {
            authentication(true);
            setRoleId(roleId);
            Swal.fire({
                title: "Login Success!",
                text: "Hello User",
                icon: "success",
                confirmButtonText: "Continue Shopping!",
            }).then((result) => {
                if (result.isConfirmed) {
                    if (message === 0) {
                        navigate("/home");
                    } else {
                        localStorage.setItem("id", JSON.stringify(message));
                        navigate("/home");
                    }
                }
            });
        } else {
            await Swal.fire({
                title: "Login Failure",
                text: "Invalid email or password",
                icon: "warning",
                confirmButtonText: "Try Again",
            });
        }
    };

    const loginHandler = (e) => {
        e.preventDefault();
        loginVerification({email, password});
    };

    return (
        <div className="mx-4 my-2">
            <form method="post" onSubmit={loginHandler}>
                <div className="input-group col-lg mt-3 my-md-none">
                    <label htmlFor="useremail" className="input-group-text">
                        <i className="bi bi-envelope"></i>
                    </label>
                    <input
                        id="useremail"
                        type="email"
                        className="form-control input-box"
                        maxLength="40"
                        placeholder="Email Address"
                        autoComplete="off"
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>
                <div className="input-group col-lg mt-3 my-md-none">
                    <label htmlFor="userpass" className="input-group-text">
                        <i className="bi bi-key"></i>
                    </label>
                    <input
                        id="userpass"
                        type={passwordFieldType}
                        className="form-control input-box"
                        maxLength="16"
                        minLength="8"
                        placeholder="••••••••"
                        autoComplete="off"
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                    <button type="button" onClick={togglePasswordVisibility} className="btn">
                        {showPassword ? <i className="bi bi-eye"></i> : <i className="bi bi-eye-slash"></i>}
                    </button>
                </div>
                <div className="fw-light mt-3">
                    Don't have an account?&nbsp;
                    <Link to="/signup" className="link">
                        Sign Up
                    </Link>
                </div>
                <div className="my-4 text-center">
                    <input
                        type="submit"
                        className="btn btn-lg btn-outline-dark"
                        id="usersubmit"
                        rows="3"
                        value={"Login!"}
                    />
                </div>
            </form>
        </div>
    );
};

export default Form;