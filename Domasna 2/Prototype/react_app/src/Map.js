import React, {useEffect, useState, useRef} from "react";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import "leaflet-routing-machine";
import "leaflet-routing-machine/dist/leaflet-routing-machine.css";
import blueMarker from "./marker_blue.svg";
import redMarker from "./marker_red.svg";

const Map = () => {
    // stores info gotten from api
    const [data, setData] = useState([]);
    // stores which link was picked to fetch which data from api
    const [selectedLink, setSelectedLink] = useState(null);
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
        if (selectedLink) {
            fetch(`http://localhost:9090/omm/api/${selectedLink}`)
                .then((response) => response.json())
                .then((data) => {
                    setData(data);
                });
        }
    }, [selectedLink]);

    // populates map with pins, populates pins with popups
    useEffect(() => {
        if (map.current && data.length > 0) {
            map.current.eachLayer((layer) => {
                if (layer instanceof L.Marker) {
                    map.current.removeLayer(layer);
                }
            });

            data.forEach((item) => {
                const marker = L.marker([item.latitude, item.longitude], {
                    icon: placeIcon,
                }).addTo(map.current);

                marker.bindPopup(
                    `<b>Name: ${item.name}</b><br>Type: ${item.type}<br>English name: ${item.en_name}<br><button id="getDirectionsBtn">Get Directions</button>`
                ).on("click", () => {
                    marker.openPopup();

                    const getDirectionsBtn = document.getElementById("getDirectionsBtn");
                    // generates directions from pin user's location to the selected pin and adds them to the map
                    if (getDirectionsBtn) {
                        getDirectionsBtn.addEventListener("click", () => {
                            if (navigator.geolocation) {
                                navigator.geolocation.getCurrentPosition(
                                    (position) => {
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
                                    }
                                );
                            }
                        });
                    }
                });
            });
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    const {latitude, longitude} = position.coords;
                    const userMarker = L.marker([latitude, longitude], {
                        icon: userIcon,
                    }).addTo(map.current);
                    userMarker
                        .bindPopup("Your Current Location")
                        .openPopup();
                }
            );
        }
    }, [placeIcon, data, userIcon, directions]);

    // clears old data so it isn't mixed with the new data
    const displayByType = (link) => {
        setData([]);
        setSelectedLink(link);
    };

    const cancelDirections = () => {
        if (directions) {
            directions.setWaypoints([]);
            map.current.removeControl(directions);
            setDirections(null);
        }
    };

    return (
        <div>
            <div>
                <div>
                    <button onClick={() => displayByType("all")}>List all</button>
                    <button onClick={() => displayByType("amenity")}>List amenities</button>
                    <button onClick={() => displayByType("tourism")}>List tourism</button>
                    <button onClick={() => displayByType("historic")}>List historic</button>
                    <button onClick={() => displayByType("archaeological_site")}>List archaeological sites</button>
                    <button onClick={() => displayByType("artwork")}>List artworks</button>
                    <button onClick={() => displayByType("library")}>List libraries</button>
                    <button onClick={() => displayByType("memorial")}>List memorials</button>
                    <button onClick={() => displayByType("monument")}>List monuments</button>
                    <button onClick={() => displayByType("tomb")}>List tombs</button>
                    <button onClick={() => displayByType("worship")}>List places of worship</button>
                    <button onClick={() => displayByType("museum")}>List museums</button>
                </div>

                <button onClick={cancelDirections}>Cancel Directions</button>

                <table>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Type</th>
                        <th>English Name</th>
                        <th>Latitude</th>
                        <th>Longitude</th>
                    </tr>
                    </thead>
                    <tbody>
                    {data.map((item) => (
                        <tr key={item.name}>
                            <td>{item.name}</td>
                            <td>{item.type}</td>
                            <td>{item.en_name}</td>
                            <td>{item.latitude}</td>
                            <td>{item.longitude}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            </div>

            <div>
                <div id="map" style={{width: "100%", height: "500px"}}></div>
            </div>
        </div>
    );
};

export default Map;
