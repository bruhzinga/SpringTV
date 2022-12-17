import {useRef, useState} from "react";
import {useNavigate} from "react-router-dom";
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
      /*  <form>
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

        </form>*/

        <div className="container">

            <div className="screen">

                <div className="screen__content">
                    <form className="login">
                        <h1 >Login</h1>
                        <div className="login__field">
                            <i className="login__icon fas fa-user"></i>
                            <input   ref={nameRef}  type="text" className="login__input" placeholder="User name / Email">
                            </input>
                        </div>
                        <div className="login__field">
                            <i className="login__icon fas fa-lock"></i>
                            <input ref={passwordRef} type="password" className="login__input" placeholder="Password">
                            </input>
                        </div>
                        <button className="button login__submit" onClick={submitHandle}>
                            <span className="button__text">Log In Now</span>
                            <i className="button__icon fas fa-chevron-right"></i>
                        </button>
                        <button onClick={HandleForgot} className="button login__submit">Forgot the password?</button>

                        <div className={isForgetPassword ? "displayed" : "nonDisplayed"}>
                            <input type="email" ref={emailRef} placeholder="typeYourEmail"/>
                            <button onClick={SendEmailHandle}>Send</button>
                        </div>
                    </form>

                </div>
                <div className="screen__background">
                    <span className="screen__background__shape screen__background__shape4"></span>
                    <span className="screen__background__shape screen__background__shape3"></span>
                    <span className="screen__background__shape screen__background__shape2"></span>
                    <span className="screen__background__shape screen__background__shape1"></span>
                </div>
            </div>
        </div>
    );
}

