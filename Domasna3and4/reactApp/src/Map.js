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
    const apiHost = process.env.REACT_APP_API_HOST;
    const url = `${apiHost}/omm/api/`;
    const [data, setData] = useState([]);
    // variable for the cities information
    const [cities, setCities] = useState([]);
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

    useEffect(function getCities() {
        fetchCitiesData();
    }, []);

    useEffect(
        function loadMap(){
        if (!map.current) {
            const newMap = L.map("map").setView([41.6086, 21.7453], 8);
            L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png").addTo(
                newMap
            );
            map.current = newMap;
        }
    }, []);

    useEffect(
        function fetchDataFromApi(){
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

    useEffect(
        function placePinsOnMap() {
        if (map.current && cities.length > 0) {
            map.current.eachLayer((layer) => {
                if (layer instanceof L.Marker || layer instanceof L.Circle) {
                    map.current.removeLayer(layer);
                }
            });

            const pinsToUse = filteredData.length > 0 ? filteredData : data;

            const distances = cities.map((city) => {
                return pinsToUse.map((item) => {
                    return map.current.distance(
                        [item.latitude, item.longitude],
                        [city.latitude, city.longitude]
                    );
                });
            });
            const maxDistance = Math.max(...distances.flat());

            pinsToUse.forEach((item) => {
                // Calculate the distance to the nearest city for each location
                const distancesToCities = cities.map((city) => {
                    return map.current.distance(
                        [item.latitude, item.longitude],
                        [city.latitude, city.longitude]
                    );
                });

                // Find the minimum distance
                const minDistance = Math.min(...distancesToCities);

                // normalization because without it the circles are as big as the entire world map
                const normalizedDistance = minDistance / maxDistance;

                // Normalization is scaled up to 50 meters,so the radii of the circles are gonna be between 0m and 50m
                const radius = normalizedDistance * 50000;

                // Add a marker for each location
                const marker = L.marker([item.latitude, item.longitude], {
                    icon: placeIcon,
                }).addTo(map.current);

                // and add a circle for each location
                L.circle([item.latitude, item.longitude], {
                    radius: radius,
                    color: 'blue',
                    fillColor: '#00e1ff',
                    fillOpacity: 0.5,
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
    }, [placeIcon, data, userIcon, directions, filteredData, cities]);



    const displayByType = (link) => {
        setData([]);
        setSelectedCategory(link);
    };

    useEffect(
        function getFilteredData() {
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

    // queries the OverpassAPI to obtain information
    // for cities or towns in the bounding box surrounding Macedonia
    const fetchCitiesData = () => {
        let query = `[out:json];(
            node["place"~"city|town"](41.1,20.9,42.4,23.1);
        );out;`;

        fetch(`https://overpass-api.de/api/interpreter?data=${encodeURIComponent(query)}`)
            .then((response) => response.json())
            .then((osmData) => {
                const citiesData = osmData.elements.map((element) => {
                    return {
                        id: element.id,
                        name: element.tags.name,
                        latitude: element.lat,
                        longitude: element.lon
                    };
                });
                setCities(citiesData);
            })
            .catch((error) => {
                console.error("Error fetching cities data:", error);
            });
    };


    return (
        <div>
            <div className="d-flex flex-row align-items-center top-bar">
                <div className="p-2" style={{fontSize: "18px"}}>
                    OMM Historic and Cultural Locations
                </div>
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
                    Category:
                </div>
                <div className="p-2">
                    <select
                        className="form-control"
                        onChange={handleCategoryChange}
                    >
                        <option value="all">All</option>
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
                    <Link to="/AboutUs">
                        <button className="cancel-button">About Us</button>
                    </Link>
                </div>
            </div>
            <div id="map" className="map-container"></div>
        </div>
    );
};

export default Map;
