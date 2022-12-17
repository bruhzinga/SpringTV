import "./AllMoviesPage.css"
import {useEffect, useState} from "react";
import {useQuery} from "react-query";
import MoviesService from "../../services/MoviesService.js";
//import css from AllMoviesPage.css

function Search() {
    return null;
}

export function AllMoviesPage() {
    const [page, setPage] = useState(1);
    const [content, setContent] = useState([]);

    const {refetch: refetchMovies} = useQuery("movies", () => MoviesService.getMovies(page));

    useEffect(() => {
        refetchMovies().then((response) => {
            console.log(response.data)
            setContent(response.data);
        });
    }, [page]);


    return (
        <>


            <table className="MovieTable">
                <thead>
                <tr>
                    <th>Title</th>
                    <th className="genre">Genre</th>
                    <th className="year">Year</th>
                    <th>Number of views</th>
                    <th>Director</th>
                    <th>Poster</th>
                </tr>
                </thead>
                <tbody>
                {content && content.map((movie) => {
                    return (

                        <tr key={movie.id}>

                            <td><a href={`/movies/${movie.id}`}> {movie.title}</a></td>
                            <td>{movie.genre}</td>
                            <td>{movie.year}</td>
                            <td>{movie.numberOfViews}</td>
                            <td>{movie.director}</td>
                            <td>
                                <img className="IMAGE" src={`http://localhost:8080/images/getThumbnail/${movie.id}`} alt=""/>
                            </td>

                        </tr>

                    );
                })}
                </tbody>
            </table>
            <div>
                <button onClick={() => setPage((prevPage) => {
                    if (prevPage === 1) {
                        return prevPage;
                    }
                    return prevPage - 1;
                })}>Previous
                </button>
                <button onClick={() => setPage((prevPage) => prevPage + 1)}>Next</button>
            </div>
        </>

    )
}