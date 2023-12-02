import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Home from "./Home";
import Map from "./Map";


function App() {
    return (
        <Router>
            <Routes>
                <Route exact path="/" element={<Home/>}/>
                <Route path='/map' exact={true}
                       element={<Map/>}/>
            </Routes>
        </Router>
    )
}

export default App;
