package com.esia.big_shop_backend.presentation.rest;

import com.esia.big_shop_backend.application.usecase.address.*;
import com.esia.big_shop_backend.application.usecase.address.command.CreateAddressCommand;
import com.esia.big_shop_backend.application.usecase.address.command.UpdateAddressCommand;
import com.esia.big_shop_backend.presentation.dto.request.address.CreateAddressRequest;
import com.esia.big_shop_backend.presentation.dto.request.address.UpdateAddressRequest;
import com.esia.big_shop_backend.presentation.dto.response.address.AddressResponse;
import com.esia.big_shop_backend.presentation.mapper.AddressRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
@Tag(name = "Address", description = "Address management APIs")
public class AddressController {

    private final CreateAddressUseCase createAddressUseCase;
    private final GetUserAddressesUseCase getUserAddressesUseCase;
    private final UpdateAddressUseCase updateAddressUseCase;
    private final DeleteAddressUseCase deleteAddressUseCase;
    private final AddressRestMapper mapper;

    @PostMapping
    @Operation(summary = "Create a new address")
    public ResponseEntity<AddressResponse> createAddress(@RequestBody @Valid CreateAddressRequest req, Principal principal) {
        var address = createAddressUseCase.execute(
                CreateAddressCommand.builder()
                        .userEmail(principal.getName())
                        .fullName(req.getFullName())
                        .phone(req.getPhone())
                        .street(req.getStreet())
                        .city(req.getCity())
                        .zipCode(req.getZipCode())
                        .country(req.getCountry())
                        .makeDefault(req.isMakeDefault())
                        .build()
        );
        return ResponseEntity.ok(mapper.toResponse(address));
    }

    @GetMapping
    @Operation(summary = "Get user addresses")
    public ResponseEntity<List<AddressResponse>> getUserAddresses(Principal principal) {
        var list = getUserAddressesUseCase.execute(principal.getName()).stream().map(mapper::toResponse).toList();
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an address")
    public ResponseEntity<AddressResponse> updateAddress(@PathVariable Long id,
                                                         @RequestBody @Valid UpdateAddressRequest req,
                                                         Principal principal) {
        var updated = updateAddressUseCase.execute(UpdateAddressCommand.builder()
                .userEmail(principal.getName())
                .addressId(id)
                .fullName(req.getFullName())
                .phone(req.getPhone())
                .street(req.getStreet())
                .city(req.getCity())
                .zipCode(req.getZipCode())
                .country(req.getCountry())
                .build());
        return ResponseEntity.ok(mapper.toResponse(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an address")
    public ResponseEntity<Map<String, String>> deleteAddress(@PathVariable Long id, Principal principal) {
        deleteAddressUseCase.execute(principal.getName(), id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Address deleted successfully");
        return ResponseEntity.ok(response);
    }
}
