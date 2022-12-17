import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import MoviesService from "../../services/MoviesService.js";
import moviesService from "../../services/MoviesService.js";
import {Comments} from "../../components/Comments.jsx";
import {useQuery} from "react-query";
import './MoviePage.css';
import UserService from "../../services/UserService.js";


export function MoviePage() {

    const {id} = useParams();

    const [url, setUrl] = useState("");
    const [currentMovie, setCurrentMovie] = useState({});
    const [comments, setComments] = useState([{}]);
    const [isInFavorites, setIsInFavorites] = useState(false);
    const [actors, setActors] = useState([]);

    const {refetch: refetchComments} = useQuery("comments", () => moviesService.getComments(id))
    const {refetch: refetchActors} = useQuery("actors", () => moviesService.getMovieActors(id))
    const {refetch: refetchFavorites, data: favourites} = useQuery("favorites", () => UserService.getFavorites())


    useEffect(() => {

        MoviesService.getMovie(id).then((response) => {
            setCurrentMovie(response.data);
        });
        MoviesService.getVideo(id).then((response) => response.blob()).then((blob) => {
            const url = URL.createObjectURL(blob);
            setUrl(url);
        });

        refetchComments().then((response) => {
            console.log(response);
            setComments(response.data);
        });

        refetchActors().then((response) => {
            console.log(response);
            setActors(response.data);
        });


    }, []);


    useEffect(() => {
        if (favourites && currentMovie) {
            setIsInFavorites(favourites.some((movie) => movie.title === currentMovie.title));
        }
    }, [favourites, currentMovie]);


    const newCommentAddedHandler = () => {
        refetchComments().then((response) => {
            console.log(response);
            setComments(response.data);
        });
    }


    function favouritesHandler(e) {
        e.preventDefault();
        if (isInFavorites) {
            UserService.removeFromFavorites(id).then(() => {
                refetchFavorites()
            });
        } else {
            UserService.addToFavorites(id).then(() => {
                refetchFavorites()
            });
        }
    }

    return (
        <>
            <h1>Title : {currentMovie.title}</h1>

            <div>
                <button onClick={favouritesHandler} className={isInFavorites ? `isInFavourites` : `OutOfFavourites`}>
                    {isInFavorites ? `Remove from favourites` : `Add to favourites`}
                </button>
            </div>

            <div>
                <span>
                    <h2>Description : </h2>
                    <p>{currentMovie.description}</p>
                </span>
            </div>

            <div className="table">
                <table>
                    <thead>
                    <tr>
                        <th>Actor Name</th>
                        <th>Character Name</th>
                        <th>Actor Portrait</th>
                    </tr>
                    </thead>
                    {actors && actors.map((actor) => (
                        <tr key={actor.id + "actor"}>
                            <td>{actor.name}</td>
                            <td>{actor.role}</td>
                            <td><img src={`http://localhost:8080/images/people/${actor.imageId}`} alt=""/></td>
                        </tr>
                    ))}
                </table>
            </div>


            <div>
                <h2>Trailer</h2>
                <video controls={true} src={`http://localhost:8080/movies/trailer/${id}`}></video>
            </div>

            <div>
                <h2>Film</h2>
                <video controls={true} src={url}></video>
            </div>


            <div>
                <Comments setComments={setComments} comments={comments} id={id}
                          newCommentAddedHandler={newCommentAddedHandler}></Comments>
            </div>


        </>
    );


}