import AuthenticationService from "../services/UserService.js";
import {useRef} from "react";
import {useNavigate} from "react-router-dom";


export function RegisterPage() {

    const nameRef = useRef();
    const emailRef = useRef();
    const passwordRef = useRef();
    const navigator = useNavigate();

    function submitHandle() {

        const username = nameRef.current.value;
        const email = emailRef.current.value;
        const password = passwordRef.current.value;

        AuthenticationService.register(username, password, email).then(async (responce) => {
            console.log(responce);
            alert(responce.data);
            navigator("/login");
        }).catch(response => {
            console.log("Ошибка")
            alert(response.response.data);
        });
    }


    return (
        <>
            <form>
                <h3>Register</h3>
                <div className="form-group">
                    <label>Username</label>
                    <input ref={nameRef} type="text" className="form-control" placeholder="Username"/>
                </div>
                <div className="form-group">
                    <label>Password</label>
                    <input ref={passwordRef} type="password" className="form-control" placeholder="Password"/>
                </div>
                <div className="form-group">
                    <label>Email</label>
                    <input ref={emailRef} type="email" className="form-control" placeholder="Email"/>
                </div>


                <button type="button" onClick={submitHandle} className="btn btn-primary btn-block">Register</button>

            </form>
        </>
    );
}