import AuthenticationService from "../../services/UserService.js";
import { useRef } from "react";
import { useNavigate } from "react-router-dom";
import "./RegisterPage.css";

export function RegisterPage() {
    const nameRef = useRef();
    const emailRef = useRef();
    const passwordRef = useRef();
    const navigator = useNavigate();

    function submitHandle() {
        const username = nameRef.current.value;
        const email = emailRef.current.value;
        const password = passwordRef.current.value;

        AuthenticationService.register(username, password, email).then(
            async (responce) => {
                console.log(responce);
                alert(responce.data);
                navigator("/login");
            }
        ).catch(response => {
            console.log("Ошибка")
            alert(response.response.data);
        });
    }

    return (
        <div className="RegisterPage container mt-5">
            <div className="row">
                <div className="col-md-6 offset-md-3">
                    <form>
                        <h3 className="text-center mb-4">Register</h3>
                        <div className="form-group">
                            <label htmlFor="name">Username</label>
                            <input ref={nameRef} type="text" className="form-control" id="name" placeholder="Username" />
                        </div>
                        <div className="form-group">
                            <label htmlFor="password">Password</label>
                            <input ref={passwordRef} type="password" className="form-control" id="password" placeholder="Password" />
                        </div>
                        <div className="form-group">
                            <label htmlFor="email">Email</label>
                            <input ref={emailRef} type="email" className="form-control" id="email" placeholder="Email" />
                        </div>
                        <button type="button" onClick={submitHandle} className="btn btn-primary btn-block">
                            Register
                        </button>
                    </form>
                </div>
            </div>
        </div>

    );
}
