import { useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import "./LoginPage.css";
import AuthenticationService from "../../services/UserService.js";

export function LoginPage() {
    const nameRef = useRef();
    const passwordRef = useRef();
    const emailRef = useRef();
    const navigator = useNavigate();

    const [isForgetPassword, setIsForgetPassword] = useState(false);

    function submitHandle() {
        const username = nameRef.current.value;
        const password = passwordRef.current.value;

        AuthenticationService.login(username, password)
            .then((responce) => {
                console.log(responce.data);
                localStorage.setItem("token", JSON.stringify(responce.data));
                alert(responce.data);
                navigator("/allMovies");
            })
            .catch((response) => {
                console.log("Ошибка");
                alert(response.response.data);
            });
    }

    function HandleForgot(e) {
        e.preventDefault();
        setIsForgetPassword((prevState) => !prevState);
    }

    function SendEmailHandle(e) {
        e.preventDefault();
        const email = emailRef.current.value;

        AuthenticationService.sendPasswordByEmail(email)
            .then((responce) => {
                console.log(responce.data);
                localStorage.setItem("token", JSON.stringify(responce.data));
                alert(responce.data);
            })
            .catch((response) => {
                console.log("Ошибка");
                alert(response.response.data);
            });
    }

    return (
        <div className="LoginPage container mt-5">
            <div className="row">
                <div className="col-md-6 offset-md-3">
                    <form>
                        <h3 className="text-center mb-4">Login</h3>
                        <div className="form-group">
                            <label htmlFor="username">Username</label>
                            <input
                                ref={nameRef}
                                type="text"
                                className="form-control"
                                id="username"
                                placeholder="Username"
                            />
                        </div>
                        <div className="form-group">
                            <label htmlFor="password">Password</label>
                            <input
                                ref={passwordRef}
                                type="password"
                                className="form-control"
                                id="password"
                                placeholder="Password"
                            />
                        </div>
                        <button
                            type="button"
                            onClick={submitHandle}
                            className="btn btn-primary btn-block"
                        >
                            Login
                        </button>
                        <button onClick={HandleForgot} className="btn btn-link">
                            Forgot the password
                        </button>
                        <div
                            className={`form-group ${
                                isForgetPassword ? "displayed" : "nonDisplayed"
                            }`}
                        >
                            <label htmlFor="email">Email</label>
                            <input
                                ref={emailRef}
                                type="email"
                                className="form-control"
                                id="email"
                                placeholder="Email"
                            />
                        </div>
                        <button onClick={SendEmailHandle} className="btn btn-primary btn-block">
                            Send
                        </button>
                    </form>
                </div>
            </div>
        </div>
    );
}

