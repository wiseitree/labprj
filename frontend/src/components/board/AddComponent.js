import { useRef, useState } from 'react';
import { postAdd } from '../../api/boardApi';
import useCustomMove from '../../hooks/useCustomMove';
import FetchingModal from "../common/FetchingModal";
import ResultModal from "../common/ResultModal";

const initState = {
    title: '',
    content: '',
    email: 'user1@aaa.com',
    writer: 'user1',
    files: [],
};


const AddComponent = () => {
    const [board, setBoard] = useState({ ...initState });
    const uploadRef = useRef()

    const [fetching, setFetching] = useState(false)
    const [result, setResult] = useState(null)

    const { moveToList } = useCustomMove();

    const handleChangeBoard = (e) => {
        board[e.target.name] = e.target.value;
        setBoard({ ...board });
    };

    const handleKeyDown = (e) => {
        if (e.key === 'Enter') handleClickAdd();
    };

    const handleClickAdd = (e) => {
        const files = uploadRef.current.files;
        const formData = new FormData();

        for (let i = 0; i < files.length; i++){
            formData.append("files", files[i]);
        }

        formData.append("title", board.title)
        formData.append("content", board.content)
        formData.append("email", board.email)
        formData.append("writer", board.writer)

        console.log(formData);
        setFetching(true)

        postAdd(formData).then(data => {
            setFetching(false);
            setResult(data.result)
        })

    };

    const closeModal = () => {
        setResult(null)
        moveToList({page:1})
    }

    const calcContentLen = (data) => {
        let curDataLen = '';
        let totalDataLen = '';

        curDataLen = board[data].length;
        if (data === 'title') totalDataLen = 80;
        if (data === 'content') totalDataLen = 2000;

        if (curDataLen > totalDataLen) {
            alert(`최대 ${totalDataLen}자 까지만 입력 가능합니다.`);
            board[data] = board[data].substring(0, totalDataLen);
            setBoard({ ...board });
        }
        return `${curDataLen}/${totalDataLen}`;
    };

    return (
        <div className="border-2 border-sky-200 mt-10 m-2 p-4">

            {fetching ? <FetchingModal/> : <></>}

            {result?
                <ResultModal
                    title = {'Board Add Result'}
                    content={`${result}번 등록 완료`}
                    callbackFn={closeModal}
                    />
                :<></>
            }

            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-right font-bold">Files</div>
                    <input ref={uploadRef}
                           className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
                           type={'file'} multiple={true}
                    >
                    </input>
                </div>
            </div>

            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-right font-bold">제목</div>
                    <input
                        className="w-4/5 p-6 rounded-r border border-solid border-neutral-500 shadow-md"
                        name="title"
                        type={'text'}
                        value={board.title}
                        onChange={handleChangeBoard}
                        autoFocus={true}
                    ></input>
                    <div className="absolute bottom-0 right-0 text-gray-500">
                        {calcContentLen('title')}
                    </div>
                </div>
            </div>

            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-1/5 p-6 text-right font-bold">내용</div>
                    <textarea
                        className="w-4/5 p-6 rounded-r border border-solid border-neutral-300 shadow-md"
                        name="content"
                        type={'text'}
                        value={board.content}
                        onChange={handleChangeBoard}
                        rows="14"
                        onKeyDown={handleKeyDown}
                    ></textarea>
                    <div className="absolute bottom-0 right-0 text-gray-500">
                        {calcContentLen('content')}
                    </div>
                </div>
            </div>

            <div className="flex justify-end p-4">
                {/*<div className="relative mb-4 flex p-4 flex-wrap items-stretch">*/}
                <button
                    type="button"
                    className="rounded p-4 mr-2 w-36 bg-gray-400 text-xl  text-white hover:bg-gray-500"
                >
                    취소
                </button>
                <button
                    type="button"
                    className="rounded p-4  w-36 bg-blue-500 text-xl  text-white hover:bg-blue-800"
                    // onClick={() => handleClickAdd(board)}
                    onClick={handleClickAdd}
                >
                    등록
                </button>
                {/*</div>*/}
            </div>
        </div>

    );


};

export default AddComponent;