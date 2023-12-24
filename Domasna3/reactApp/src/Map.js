import React, { useEffect, useState, useRef } from "react";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import "bootstrap/dist/css/bootstrap.min.css";
import "./Map.css"
import "leaflet-routing-machine";
import "leaflet-routing-machine/dist/leaflet-routing-machine.css";
import blueMarker from "./marker_blue.svg";
import redMarker from "./marker_red.svg";
import {Link} from "react-router-dom";


const Map = () => {
    const url = "https://prototype.jollycoast-8d47174b.westeurope.azurecontainerapps.io/omm/api/"
    //const url = "localhost:9090/omm/api/";
    const [data, setData] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState("all");
    // eslint-disable-next-line no-unused-vars
    const [filterText, setFilterText] = useState("");
    const [filteredData, setFilteredData] = useState([]);
    const [directions, setDirections] = useState(null);
    const map = useRef(null);
    const placeIcon = L.icon({
        iconUrl: blueMarker,
        iconSize: [32, 32],
        popupAnchor: [0, -20],
    });
    const userIcon = L.icon({
        iconUrl: redMarker,
        iconSize: [32, 32],
        popupAnchor: [0, -20],
    });

    useEffect(() => {
        if (!map.current) {
            const newMap = L.map("map").setView([41.6086, 21.7453], 8);
            L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png").addTo(
                newMap
            );
            map.current = newMap;
        }
    }, []);

    useEffect(() => {
        if (selectedCategory) {
            fetch(`${url}${selectedCategory}`)
                .then((response) => response.json())
                .then((data) => {
                    setData(data);
                });
        }
    }, [selectedCategory]);

    const handleCategoryChange = (event) => {
        const newCategory = event.target.value;
        setSelectedCategory(newCategory);
        displayByType(newCategory);
    };

    // const handleFilter = useCallback(() => {
    //     let apiUrl = `${url}filter?text=${encodeURIComponent(filterText)}`;
    //
    //     if (selectedCategory) {
    //         apiUrl += `&type=${encodeURIComponent(selectedCategory)}`;
    //     }
    //     console.log("API URL:", apiUrl); // Print the API URL for debugging
    //     console.log("category", selectedCategory);
    //     fetch(apiUrl)
    //         .then((response) => response.json())
    //         .then((data) => {
    //             setFilteredData(data);
    //         });
    // }, [filterText, selectedCategory]);

    useEffect(() => {
        if (map.current) {
            map.current.eachLayer((layer) => {
                if (layer instanceof L.Marker) {
                    map.current.removeLayer(layer);
                }
            });

            const pinsToUse = filteredData.length > 0 ? filteredData : data;
            pinsToUse.forEach((item) => {
                const marker = L.marker([item.latitude, item.longitude], {
                    icon: placeIcon,
                }).addTo(map.current);

                marker.bindPopup(
                    `<b>Name: ${item.name}</b><br>Type: ${item.type}<br>English name: ${item.en_name}<br>
                        <div class="p-2">
                            <button id="getDirectionsBtn" class="btn btn-secondary btn-sm">Get Directions</button>
                        </div>`
                ).on("click", () => {
                    marker.openPopup();

                    const getDirectionsBtn = document.getElementById("getDirectionsBtn");
                    if (getDirectionsBtn) {
                        getDirectionsBtn.addEventListener("click", () => {
                            if (navigator.geolocation) {
                                navigator.geolocation.getCurrentPosition((position) => {
                                    const { latitude, longitude } = position.coords;
                                    const waypoints = [
                                        L.latLng(latitude, longitude),
                                        L.latLng(item.latitude, item.longitude),
                                    ];

                                    if (directions) {
                                        directions.setWaypoints(waypoints);
                                    } else {
                                        const control = L.Routing.control({
                                            waypoints,
                                            routeWhileDragging: true,
                                            lineOptions: {
                                                styles: [{ color: "blue", weight: 3 }],
                                            },
                                        }).addTo(map.current);

                                        setDirections(control);
                                    }
                                });
                            }
                        });
                    }
                });
            });

            navigator.geolocation.getCurrentPosition((position) => {
                const { latitude, longitude } = position.coords;
                const userMarker = L.marker([latitude, longitude], {
                    icon: userIcon,
                }).addTo(map.current);
                userMarker.bindPopup("Your Current Location").openPopup();
            });
        }
    }, [placeIcon, data, userIcon, directions, filteredData]);

    const displayByType = (link) => {
        setData([]);
        setSelectedCategory(link);
    };

    useEffect(() => {
        let apiUrl = `${url}filter?text=${encodeURIComponent(filterText)}`;

        if (selectedCategory) {
            apiUrl += `&type=${encodeURIComponent(selectedCategory)}`;
        }
        console.log("API URL:", apiUrl);

        fetch(apiUrl)
            .then((response) => response.json())
            .then((data) => {
                setFilteredData(data);
            });
    }, [filterText, selectedCategory]);

    const cancelDirections = () => {
        if (directions) {
            directions.setWaypoints([]);
            map.current.removeControl(directions);
            setDirections(null);
        }

    };


    return (
        <div>
            <div className="d-flex flex-row align-items-center top-bar">
                <div className="mr-auto p-2">
                    <input
                        className="filter-input"
                        type="text"
                        placeholder="Enter filter text"
                        value={filterText}
                        onChange={(e) => setFilterText(e.target.value)}
                    />
                </div>

                <div className="p-2">
                    <select
                        className="form-control"
                        onChange={handleCategoryChange}
                    >
                        <option value="all">None</option>
                        <option value="amenity">Amenities</option>
                        <option value="tourism">Tourism</option>
                        <option value="historic">Historic</option>
                        <option value="archaeological_site">Archaeological Sites</option>
                        <option value="artwork">Artworks</option>
                        <option value="library">Libraries</option>
                        <option value="memorial">Memorials</option>
                        <option value="monument">Monuments</option>
                        <option value="tomb">Tombs</option>
                        <option value="worship">Places of Worship</option>
                        <option value="museum">Museums</option>
                    </select>
                </div>

                <div className="p-2">
                    <button className="cancel-button" onClick={cancelDirections}>
                        Cancel Directions
                    </button>
                </div>
                <div className="p-2 position-fixed top-0 end-0">
                    <Link to="/AboutUs"><button className="cancel-button" >About Us</button></Link>
                </div>
            </div>
            <div id="map" className="map-container"></div>
        </div>
    );
};

export default Map;
