package be.bds.bdsbes.service;

import be.bds.bdsbes.exception.ServiceException;
import be.bds.bdsbes.payload.HoaDonResponse;
import be.bds.bdsbes.payload.PhongResponse1;
import be.bds.bdsbes.utils.dto.PagedResponse;

public interface IHoaDonService {

    PagedResponse<HoaDonResponse> getHoaDon(int page, int size) throws ServiceException;

    PagedResponse<HoaDonResponse> getHoaDonByCustomer(int page, int size,String hoTen, String sdt) throws ServiceException;
}
