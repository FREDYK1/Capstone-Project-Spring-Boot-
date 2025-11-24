import axios from 'axios';

const API_BASE_URL = 'http://localhost:3000/api/titles';

export const getTitles = async () => {
    const response = await axios.get(API_BASE_URL);
    return response.data;
};

export const createTitle = async (titleData) => {
    const response = await axios.post(API_BASE_URL, titleData);
    return response.data;
};

export const updateTitle = async (id, titleData) => {
    const response = await axios.put(`${API_BASE_URL}/${id}`, titleData);
    return response.data;
};

export const deleteTitle = async (id) => {
    const response = await axios.delete(`${API_BASE_URL}/${id}`);
    return response.data;
};

export const searchTitles = async (query) => {
    const response = await axios.get(`${API_BASE_URL}/search?q=${query}`);
    return response.data;
};