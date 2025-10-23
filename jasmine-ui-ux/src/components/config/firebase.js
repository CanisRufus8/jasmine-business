// src/components/config/firebase.js
import { initializeApp } from "firebase/app";
import { getStorage } from "firebase/storage";

// Cấu hình Firebase của bạn
const firebaseConfig = {
  apiKey: "AIzaSyBCgQzKzlWPeOLlsNNJzL2Fty_3xZm_9Fw",
  authDomain: "business-app-firebase-ef09e.firebaseapp.com",
  projectId: "business-app-firebase-ef09e",
  storageBucket: "business-app-firebase-ef09e.appspot.com",
  messagingSenderId: "388106220721",
  appId: "1:388106220721:web:76acbd91b4d61c0e929e90",
};

// Khởi tạo app
const app = initializeApp(firebaseConfig);

// Khởi tạo Firebase Storage
const storage = getStorage(app);

export { storage, app as default };
