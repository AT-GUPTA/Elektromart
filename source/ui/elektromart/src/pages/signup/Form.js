import React, {useState} from "react";
import "../login/Form.css";
import {Link, useNavigate} from "react-router-dom";
import Swal from "sweetalert2";

const Form = ({role}) => {
    const [username, setUserName] = useState("");
    const [email, setEmail] = useState("");
     const navigate = useNavigate();
    
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
        
        const cart_id = localStorage.getItem("cart_id");
        const userData = {
            username,
            email,
            role,
        };
        
        if (cart_id !== null) {
            userData.cartId = cart_id;
        } else {
            userData.cartId = "0";
        }
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
                        placeholder="Passcode"
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
                { role == '1' &&
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
                }

                
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