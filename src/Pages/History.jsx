import {useQuery} from "react-query";
import UserService from "../services/UserService.js";

export function History() {

    const {data: history} = useQuery("history", () => UserService.getHistory());

    return (
        <>
            <h1>History</h1>
            <table>
                <thead>
                <tr>
                    <th>Title</th>
                    <th>UserName</th>
                    <th>Time</th>
                </tr>
                </thead>
                <tbody>
                {history && history.map((history) => {
                    return (
                        <tr key={"history" + history.id}>
                            <td>{history.title}</td>
                            <td>{history.username}</td>
                            <td>{history.time}</td>
                        </tr>
                    );
                })}
                </tbody>
            </table>
        </>
    )
}