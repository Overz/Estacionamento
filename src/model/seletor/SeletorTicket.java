package model.seletor;

public class SeletorTicket<TicketVO> implements SuperSeletor<TicketVO> {

    @Override
    public String criarFiltro(String string, TicketVO object) {
        return null;
    }

    @Override
    public boolean temFiltro(TicketVO object) {
        return false;
    }
}
