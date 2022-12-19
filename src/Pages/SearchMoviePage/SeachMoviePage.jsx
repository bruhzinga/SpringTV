import React, { useState, useEffect } from "react";
import axios from "axios";


export function SearchMoviesPage() {
    const [page, setPage] = useState(1);
    const [content, setContent] = useState([]);
    const [searchTerm, setSearchTerm] = useState("");
    const [isLoading, setIsLoading] = useState(false);
    const [error, setError] = useState(null);

    const handleSearch = (e) => {
        e.preventDefault();
        setIsLoading(true);
        setError(null);

        axios
            .post("http://localhost:8080/movies/SearchMovies", {
                columnName: "description",
                searchParameters: searchTerm,
                oracleText: "true",
                page: page,
            })
            .then((response) => {
                setContent(response.data);
                setIsLoading(false);
            })
            .catch((error) => {
                setError(error);
                setIsLoading(false);
            });
    };

    return (
        <div>
            <h1>Search Movies</h1>
            <input
                type="text"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
            />
            <button onClick={handleSearch}>Search</button>
            {isLoading && <p>Loading...</p>}
            {error && <p>Error: {error.message}</p>}
            {content && (
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
                    {content.map((movie) => (
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
                    ))}
                    </tbody>
                </table>)}
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


        </div>

    );
}

