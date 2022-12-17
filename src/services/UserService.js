import axios from "axios";
import authHeader from "./headers/authHeader.js";

const API_URL = "http://localhost:8080/users/";

const register = (login, password, email) => {
    return axios.post(API_URL + "register", {
        login,
        password,
        email,
        roleId: 2
    });
};

const login = async (login, password) => {

    return axios.post(API_URL + "login", {
        login,
        password,
    });

};

const sendPasswordByEmail = (email) => {

    return axios.post(API_URL + "forgotPassword", {
        email
    });
}

const postComment = async (id, comment) => {
    const response = await axios.post(`${API_URL}PostComment/${id}`, {comment}, {headers: authHeader()});
    return response.data;
}

const getFavorites = async () => {
    const response = await axios.get(API_URL + "favorites", {headers: authHeader()});
    return response.data;
}

const addToFavorites = async (id) => {
    const response = await axios.post(API_URL + "addFavourite", {id:id}, {headers: authHeader()});
    return response.data;
}

const removeFromFavorites = async (id) => {
    const response = await axios.delete(API_URL + "deleteFavourite", {data: {id: id}, headers: authHeader()});
    return response.data;
}

const getHistory = async () => {
    const response = await axios.get(API_URL + "getHistory", {headers: authHeader()});
    return response.data;
}

const getFavourites = async () => {
    const response = await axios.get(API_URL + "favorites", {headers: authHeader()});
    return response.data;
}

const AuthService = {
    register,
    login,
    sendPasswordByEmail,
    postComment,
    getFavorites,
    removeFromFavorites,
    addToFavorites,
    getHistory,
    getFavourites
};

export default AuthService;