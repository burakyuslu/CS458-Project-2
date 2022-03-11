import "firebase/firestore";
import firebase from 'firebase/compat/app';
import 'firebase/compat/auth';
import 'firebase/compat/firestore';

const configuration = {
    apiKey: "AIzaSyDlwGLxBNTQy1hZPoAANeKf5LnHub9WiME",
    authDomain: "covid-survey-cd79d.firebaseapp.com",
    projectId: "covid-survey-cd79d",
    storageBucket: "covid-survey-cd79d.appspot.com",
    messagingSenderId: "758505750212",
    appId: "1:758505750212:web:b0dd51d1a50207fc98d391",
    measurementId: "G-NEL2Q3Y7F6",
}

firebase.initializeApp(configuration);
const db = firebase.firestore();

export {db};
