import React, {useState} from "react";
import "../login/Form.css";
import {Link, useNavigate} from "react-router-dom";
import Swal from "sweetalert2";

const Form = ({role}) => {
    const [username, setUserName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const navigate = useNavigate();
    const [showPassword, setShowPassword] = useState(false);
    const [showConfirmPassword, setShowConfirmPassword] = useState(false);


    const togglePasswordVisibility = () => {
        setShowPassword(prevShowPassword => !prevShowPassword);
    };
    const toggleConfirmPasswordVisibility = () => {
        setShowConfirmPassword(prevShowPassword => !prevShowPassword);
    };

    const passwordFieldType = showPassword ? "text" : "password";
    const confirmPasswordFieldType = showConfirmPassword ? "text" : "password";

    const passwordsMatch = () => password === confirmPassword;

    const addUser = async (user) => {
        const res = await fetch(`http://localhost:8080/api/auth/signup`, {
            method: "POST",
            headers: {"Content-type": "application/json"},
            body: JSON.stringify(user),
        });

        const jsonResponse = await res.json();
        const status = jsonResponse["status"];
        const message = jsonResponse["message"];
        const isAccountCreated = status === "SUCCESS";

        if (isAccountCreated) {
            Swal.fire({
                title: "Success!",
                text: message,
                icon: "success",
                confirmButtonText: "Login",
            }).then((result) => {
                if (result.isConfirmed) {
                    navigate("/login");
                }
            });
        } else {
            Swal.fire({
                title: "Failure",
                text: message,
                icon: "warning",
                confirmButtonText: "Try Again",
            });
        }
    };

    const signupHandler = (e) => {
        e.preventDefault();
        
        if (!passwordsMatch()) {
            Swal.fire({
                title: "Failure",
                text: "Passwords do not match",
                icon: "warning",
                confirmButtonText: "Try Again",
            });
            return;
        }
        
        const cart_id = localStorage.getItem("cart_id");
        const userData = {
            username,
            email,
            password,
            role,
        };
        
        if (cart_id !== null) {
            userData.cartId = cart_id;
        } else {
            userData.cartId = "0";
        }
        console.log(userData);
        addUser(userData);
    };
    

    return (
        <div className="mx-4 my-2">
            <form method="post" onSubmit={signupHandler}>
                <div className="input-group col-lg mt-3 my-md-none">
                    <label htmlFor="username" className="input-group-text">
                        <i className="bi bi-person"></i>
                    </label>
                    <input
                        id="username"
                        type="text"
                        className="form-control input-box"
                        maxLength="30"
                        placeholder="Username"
                        autoComplete="off"
                        onChange={(e) => setUserName(e.target.value)}
                        required
                    />
                </div>
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
                        placeholder="Password"
                        autoComplete="off"
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                    <button type="button" onClick={togglePasswordVisibility} className="btn">
                        {showPassword ? <i className="bi bi-eye"></i> : <i className="bi bi-eye-slash"></i>}
                    </button>
                </div>
                <div className="input-group col-lg mt-3 my-md-none">
                    <label htmlFor="confirmPass" className="input-group-text">
                        <i className="bi bi-key"></i>
                    </label>
                    <input
                        id="confirmPass"
                        type={confirmPasswordFieldType}
                        className="form-control input-box"
                        maxLength="16"
                        minLength="8"
                        placeholder="Confirm Password"
                        autoComplete="off"
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        required
                    />
                    <button type="button" onClick={toggleConfirmPasswordVisibility} className="btn">
                        {showConfirmPassword ? <i className="bi bi-eye"></i> : <i className="bi bi-eye-slash"></i>}
                    </button>
                </div>
                <div className="fw-light mt-3">
                    Already have an account?&nbsp;
                    <Link to="/login" className="link">
                        Login here
                    </Link>
                    {
                        localStorage.getItem("cart_id") && (
                            <>
                                <br />
                                <span>Don't worry, your cart will be saved!</span>
                            </>
                        )
                    }
                </div>

                
                <div className="my-4 text-center">
                    <input
                        type="submit"
                        className="btn btn-lg btn-outline-dark"
                        id="usersubmit"
                        rows="3"
                        value={"Sign Up!"}
                    />
                </div>
                <hr></hr>
            </form>
        </div>
    );
};

export default Form;