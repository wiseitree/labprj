import {Suspense, lazy} from "react";
import {createBrowserRouter} from 'react-router-dom';
import {Navigate} from 'react-router-dom';
import boardRouter from "./boardRouter";

const Loading = <div>Loading.....</div>;
const Main = lazy(() => import("../pages/MainPage"));
const BoardIndex = lazy(() => import('../pages/board/IndexPage'));

const root = createBrowserRouter([
    {
        path: "",
        element: <Suspense fallback={Loading}><Main/></Suspense>
    },
    {
        path: 'board',
        element: <Suspense fallback={Loading}>
            <BoardIndex/>
        </Suspense>,
        children: boardRouter(),
    }
])

export default root;