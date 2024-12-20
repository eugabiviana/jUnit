package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Pet;
import br.com.alura.adopet.api.repository.PetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class) //As Annotations de Mock não funcionam sem essa Annotation na classe!
class ValidacaoPetDisponivelTest {

    @InjectMocks //A anotação @InjectMocks deve ser adicionada apenas ao objeto que se deseja instanciar e injetar nele os mocks.
    private ValidacaoPetDisponivel validacao;

    @Mock
    private PetRepository petRepository;

    @Mock
    private Pet pet;

    @Mock
    private SolicitacaoAdocaoDto dto;

    @Test
    void devePermitirSolicitacaoDeAdocaoPet() {

        //ARRANGE
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(pet.getAdotado()).willReturn(false);

        //ASSERT + ACT
        assertDoesNotThrow(() -> validacao.validar(dto));
    }

    @Test
    void naoDevePermitirSolicitacaoDeAdocaoPet() {

        //ARRANGE
        given(petRepository.getReferenceById(dto.idPet())).willReturn(pet);
        given(pet.getAdotado()).willReturn(true);

        //ASSERT + ACT
        assertThrows(ValidacaoException.class, () -> validacao.validar(dto));
    }
}