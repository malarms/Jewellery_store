package dao;

import model.Jewellery;
import java.util.List;
import exceptions.*;

public interface JewelleryDAO {
    void addJewellery(Jewellery jewellery) throws JewelleryAlreadyExistsException;
    Jewellery getJewelleryById(int id) throws JewelleryNotFoundException;
    List<Jewellery> getAllJewellery();
    void updateJewellery(Jewellery jewellery) throws JewelleryNotFoundException;
    void deleteJewellery(int id) throws JewelleryNotFoundException;
    List<Jewellery> getJewelleryByType(String type);
    List<Jewellery> getLowStockJewellery(int threshold);
}