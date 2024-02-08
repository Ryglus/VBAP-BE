package ryglus.VBAP.DTO.user;


public record CustomerLoginResponseDto(
        String token,
        Long id,
        String username
) {
}
