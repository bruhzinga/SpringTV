import authHeader from "./headers/authHeader.js";
import axios from "axios";

const API_URL = "http://localhost:8080/movies/";

const getMovies = async (page) => {
    const response = await axios.get(API_URL + "allwithoutmedia/" + page, {headers: authHeader()});
    return response.data;
}

const getTrailer = async (id) => {
    const response = await axios.get(API_URL + "trailer/" + id, {headers: authHeader()});
    return response;
}

const getVideo = async (id) => {
    return fetch(API_URL + "video/" + id, {headers: authHeader()});
}

const getMovie = async (id) => {
    const response = await axios.get(API_URL + "nomedia/" + id, {headers: authHeader()});
    return response;
};

const getComments = async (id) => {
    const response = await axios.get(`${API_URL + id}/comments`, {headers: authHeader()});
    return response.data;
}

const getMovieActors = async(id)=>{
    const response = await axios.get(`${API_URL + id}/actors`, {headers: authHeader()});
    return response.data;
}


const getMoviesWithSearch = async (columnName, searchParameters, page) => {
    const response = await axios.post(API_URL + "SearchMovies" , {columnName,searchParameters,oracleText:true, page}, {headers: authHeader()});
    return response.data;

};
const MoviesService = {
    getMovies,
    getTrailer,
    getVideo,
    getMovie,
    getComments,
    getMovieActors,
    getMoviesWithSearch
};

export default MoviesService;