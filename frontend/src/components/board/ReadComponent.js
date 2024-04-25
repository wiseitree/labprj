import { useEffect, useState } from 'react';
import useCustomMove from '../../hooks/useCustomMove';
import {API_SERVER_HOST, getOne} from '../../api/boardApi';
import FetchingModal from "../common/FetchingModal";


const initState = {
    bno: 0,
    title: '',
    content: '',
    writer: '',
    regTime: null,
    updateTime: null,
    email: '',
    uploadFileNames:[],
};

const modalState = {
    title: '',
    content: '',
};

const host = API_SERVER_HOST

const ReadComponent = ({bno}) => {
    const [board, setBoard] = useState(initState);
    const { moveToRead, moveToList, moveToModify } = useCustomMove();
    const [result, setResult] = useState('');
    const [fetching, setFetching] = useState(false)
    console.log("ReadComponent board = ", board);

    useEffect(() => {
        setFetching(true)

        getOne(bno)
            .then((data) => {
                setBoard(data);
                setFetching(false)
            });
    }, [bno]);

    return (
        <div className="border-2 border-sky-200 mt-10 m-2 p-4 ">

            {fetching ? <FetchingModal/> : <></>}

            {/* buttons..........start */}
            <div className="flex justify-end p-4">
                <button
                    type="button"
                    className="rounded p-4 m-2 text-xl w-32 text-white bg-blue-500 hover:bg-blue-800"
                    onClick={() => moveToList()}
                >
                    목록
                </button>
                <button
                    type="button"
                    className="rounded p-4 m-2 text-xl w-32 text-white bg-blue-500 hover:bg-blue-800"

                >
                    수정
                </button>
                <button
                    type="button"
                    className="rounded p-4 m-2 text-xl w-32 text-white bg-red-500 hover:bg-red-800"
                >
                    삭제
                </button>
            </div>

            {/* 글 정보 */}
            <div className="flex justify-end p-4">
                <div className="mr-4">글번호: {board.bno} </div>
                <div className="mr-4">작성자: {board.writer} </div>
                <div>등록시간: {board.regTime}</div>
            </div>


            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-right font-bold">제목</div>
                    <div className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md">
                        {board.title}
                    </div>
                </div>
            </div>
            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-right font-bold">내용</div>
                    <textarea
                        className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
                        rows="14"
                        value={board.content}
                        readOnly={true}
                    ></textarea>
                </div>
            </div>
        </div>
    );

}

export default ReadComponent;