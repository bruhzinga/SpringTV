import {useRef, useState} from "react";
import {useNavigate} from "react-router-dom";
import "./LoginPage.css";
import AuthenticationService from "../services/UserService.js";

export function LoginPage() {

    const nameRef = useRef();
    const passwordRef = useRef();
    const emailRef = useRef();
    const navigator = useNavigate();

    const [isForgetPassword, setIsForgetPassword] = useState(false);

    function submitHandle() {
        const username = nameRef.current.value;
        const password = passwordRef.current.value;

        AuthenticationService.login(username, password).then(async (responce) => {
            console.log(responce.data);
            localStorage.setItem("token", JSON.stringify(responce.data));
            alert(responce.data);
            navigator("/allMovies");
        }).catch(response => {
            console.log("Ошибка")
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

        AuthenticationService.sendPasswordByEmail(email).then(async (responce) => {
            console.log(responce.data);
            localStorage.setItem("token", JSON.stringify(responce.data));
            alert(responce.data);
        }).catch(response => {
            console.log("Ошибка")
            alert(response.response.data);
        });

    }

    return (
        <form>
            <h3>Login</h3>
            <div className="form-group">
                <label>Username</label>
                <input ref={nameRef} type="text" className="form-control" placeholder="Username"/>
            </div>
            <div className="form-group">
                <label>Password</label>
                <input ref={passwordRef} type="password" className="form-control" placeholder="Password"/>
            </div>


            <button  type="button" onClick={submitHandle} className="btn btn-primary btn-block">Register</button>

            <button onClick={HandleForgot}>Forgot the password, asshole?</button>

            <div className={isForgetPassword ? "displayed" : "nonDisplayed"}>
                <input type="email" ref={emailRef} placeholder="typeYourEmail"/>
                <button onClick={SendEmailHandle}>Send</button>
            </div>

        </form>
    );
}