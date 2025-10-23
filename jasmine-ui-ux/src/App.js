import React from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
} from "react-router-dom";
import { AuthProvider } from "./components/context/AuthContext";
import Navbar from "./components/commons/navbar/Navbar";
import Login from "./components/authen/Login";
import ForgotPassword from "./components/authen/ForgotPassword";
import ResetPassword from "./components/authen/ResetPassword";
import ProductList from "./components/client-pages/products/product-list/ProductList";
import AddNewProduct from "./components/admin/product-management/product-Innovation/AddNewProduct";
import ProductListAdmin from "./components/admin/product-management/product-list-admin/ProductListAdmin";
import "./App.css";
import UpdateProduct from "./components/admin/product-management/product-Innovation/UpdateProduct";
import { ToastContainer, toast } from "react-toastify";
import SignUp from "./components/authen/SignUp";
import { ProductDetail } from "./components/client-pages/product-details/ProductDetail";
import Footer from "./components/commons/footer/Footer";
const toastConfig = {
  position: "top-center",
  autoClose: 3000,
};
function App() {
  return (
    <AuthProvider>
      <Router>
        <Navbar />
        <Routes>
          <Route path="/" element={<ProductList />} />
          <Route path="/login" element={<Login />} />
          <Route path="/sign-up" element={<SignUp />} />
          <Route path="/forgot-password" element={<ForgotPassword />} />
          <Route path="/reset-password" element={<ResetPassword />} />
          <Route path="/add-new" element={<AddNewProduct />} />
          <Route path="/admin/products" element={<ProductListAdmin />} />
          <Route path="/edit/:itemId" element={<UpdateProduct />} />
          <Route path="*" element={<Navigate to="/" />} />
          <Route path="/products/detail/:itemId" element={<ProductDetail />} />
        </Routes>
        <Footer />
      </Router>

      <ToastContainer {...toastConfig} />
    </AuthProvider>
  );
}

export default App;
