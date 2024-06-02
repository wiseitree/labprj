import AddComponent from "../../components/board/AddComponent";
import TypeComponent from "../../components/board/TypeComponent";
import TempComponent from "../../components/board/TempComponent";

const AddPage = () => {
    return (
        <div className="p-4 w-full bg-white">
            <div className="text-3xl font-extrabold">게시판 등록</div>
            {/*<AddComponent />*/}
            {/*<TypeComponent/>*/}
            <TempComponent/>
        </div>
    );
};

export default AddPage;