import axios from 'axios';

export const API_SERVER_HOST = 'http://localhost:8080';
const host = `${API_SERVER_HOST}/api/board`;

export const getOne = async (bno) => {
    let url = `${host}/${bno}`;
    const res = await axios.get(url);

    return res.data;
};

export const getList = async (pageParam, searchParam) => {
    const { page, size } = pageParam;
    const { title, content, keyword } = searchParam;
    const res = await axios.get(`${host}/list`, {
        params: {
            page: page,
            size: size,
            title: title,
            content: content,
            keyword: keyword,
        },
    });

    return res.data;
};

export const postAdd = async (boardObj) => {
    const header = {headers: {"Content-Type": "multipart/form-data"}}
    const res = await axios.post(`${host}/`, boardObj, header);
    return res.data;
};