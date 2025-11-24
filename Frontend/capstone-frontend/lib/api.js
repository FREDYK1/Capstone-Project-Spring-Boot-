import axios from 'axios';

const API_BASE_URL = '/api/engineer-titles';

export const getTitles = async () => {
    const response = await axios.get(`${API_BASE_URL}/all`);
    return response.data;
};

export const getTitleById = async (id) => {
    const response = await axios.get(`${API_BASE_URL}/${id}`);
    return response.data;
};

export const updateTitle = async (id, titleData) => {
    const response = await axios.put(`${API_BASE_URL}/${id}`, titleData);
    return response.data;
};

export const deleteTitle = async (id) => {
    const response = await axios.delete(`${API_BASE_URL}/${id}`);
    return response.data;
}

export const createTitle = async (titleData) => {
    const response = await axios.post(API_BASE_URL, titleData);
    return response.data;
};