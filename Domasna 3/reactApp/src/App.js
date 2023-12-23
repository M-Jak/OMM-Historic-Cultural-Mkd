import 'bootstrap/dist/css/bootstrap.min.css';

import './App.css';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Map from "./Map";


function App() {
    return (
        <Router>
            <Routes>
                <Route exact path="/" element={<Map/>}/>
                <Route path='/map' exact={true}
                       element={<Map/>}/>
            </Routes>
        </Router>
    )
}

export default App;
