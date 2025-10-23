import React, { useState, useEffect } from "react";
import ProductCard from "../product-card/ProductCard";
import "./product-list.css";
import { ProductService } from "../../../misc/ProductService";
import Pagination from '@mui/material/Pagination';

const ProductList = () => {
  const [products, setProducts] = useState([]);
  const [page, setPage] = useState(1);
  const [pageSize, setPageSize] = useState(20);
  const [count, setCount] = useState(0);

  const getRequestParams = (page, pageSize) => {
    let params = {};

    if (page) {
      params["page"] = page - 1;
    }
    if (pageSize) {
      params["size"] = pageSize;
    }
    return params;
  };

  const handlePageChange = (event, value) => {
    setPage(value);
  };

  const showProducts = () => {
    const params = getRequestParams(page, pageSize);

    ProductService.getAllProductsUser(params)
      .then((response) => {
        console.log("PRODUCTS", response.data);
        setProducts(response.data.content);
        setCount(response.data.totalPages);
      })
      .catch((err) => console.error);
  };

  useEffect(showProducts, [page, pageSize]);

  return (
    <div className="container-fluid container-max-width">
      <div className="product-list-container">
        {products.map((product, index) => (
          <ProductCard
            key={index}
            id={product.id}
            name={product.name}
            avatarURL={product.avatarURL}
            title={product.title}
            retailPrice={product.retailPrice}
            discountPercent={product.discountPercent}
            salePrice={product.salePrice}
          />
        ))}
      </div>
      <div className="center">
        <Pagination
          color="primary"
          className="my-3"
          count={count}
          page={page}
          siblingCount={1}
          boundaryCount={1}
          variant="outlined"
          onChange={handlePageChange}
        />
      </div>
    </div>
  );
};

export default ProductList;
