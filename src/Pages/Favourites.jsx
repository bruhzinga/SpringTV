import {useQuery} from "react-query";
import UserService from "../services/UserService.js";

export function Favourites() {

    const {data: history} = useQuery("history", () => UserService.getFavourites());

    return (
        <>
        <h1>Favourites</h1>
        <table>
            <thead>
            <tr>
                <th>Title</th>
            </tr>
            </thead>
            <tbody>
            {history && history.map((history) => {
                return (
                    <tr key={"history"+history.id}>
                        <td>{history.title}</td>
                    </tr>
                );
            })}
            </tbody>
        </table>
        </>
    )
}