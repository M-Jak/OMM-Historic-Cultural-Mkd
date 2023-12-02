import React, { useEffect, useState, useRef } from "react";
import L from "leaflet";
import "leaflet/dist/leaflet.css";
import blueMarker from "./marker_blue.svg";
import redMarker from "./marker_red.svg";

const List = () => {
    const [state, setState] = useState([]);
    const [selectedLink, setSelectedLink] = useState(null);
    const mapRef = useRef(null);
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
        if (!mapRef.current) {
            const newMap = L.map("map").setView([41.6086, 21.7453], 8);
            L.tileLayer("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png").addTo(
                newMap
            );
            mapRef.current = newMap;
        }
    }, []);

    useEffect(() => {
        if (selectedLink) {
            fetch(`http://localhost:8080/omm/api/${selectedLink}`)
                .then((response) => response.json())
                .then((data) => {
                    setState(data);
                });
        }
    }, [selectedLink]);

    useEffect(() => {
        if (mapRef.current && state.length > 0) {
            mapRef.current.eachLayer((layer) => {
                if (layer instanceof L.Marker) {
                    mapRef.current.removeLayer(layer);
                }
            });

            state.forEach((item) => {
                const marker = L.marker(
                    [item.latitude, item.longitude],
                    { icon: placeIcon }
                ).addTo(mapRef.current);
                marker
                    .bindPopup(
                        `<b>Name: ${item.name}</b><br>Type: ${item.type}<br>English name: ${item.en_name}`
                    )
                    .openPopup();
            });

            // Add marker for the user's current location
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    const { latitude, longitude } = position.coords;
                    const userMarker = L.marker([latitude, longitude],{icon:userIcon}).addTo(
                        mapRef.current
                    );
                    userMarker.bindPopup("Your Current Location").openPopup();
                },
                (error) => {
                    console.error("Error getting current location:", error.message);
                }
            );
        }
    }, [placeIcon, state, userIcon]);

    const displayByType = (link) => {
        setState([]);
        setSelectedLink(link);
    };

    return (
        <div>
            <div>
                <div>
                    <button onClick={() => displayByType('all')}>List all</button>
                    <button onClick={() => displayByType('amenity')}>List amenities</button>
                    <button onClick={() => displayByType('tourism')}>List tourism</button>
                    <button onClick={() => displayByType('historic')}>List historic</button>
                    <button onClick={() => displayByType('archaeological_site')}>List archaeological sites</button>
                    <button onClick={() => displayByType('artwork')}>List artworks</button>
                    <button onClick={() => displayByType('library')}>List libraries</button>
                    <button onClick={() => displayByType('memorial')}>List memorials</button>
                    <button onClick={() => displayByType('monument')}>List monuments</button>
                    <button onClick={() => displayByType('tomb')}>List tombs</button>
                    <button onClick={() => displayByType('worship')}>List places of worship</button>
                    <button onClick={() => displayByType('museum')}>List museums</button>
                </div>

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
                    {state.map(item => (
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
                <div id="map" style={{ width: "100%", height: "500px" }}></div>
            </div>
        </div>
    );
};

export default List;
