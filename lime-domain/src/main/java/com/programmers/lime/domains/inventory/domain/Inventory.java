package com.programmers.lime.domains.inventory.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.programmers.lime.common.model.Hobby;
import com.programmers.lime.domains.BaseEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "inventories")
public class Inventory extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(name = "hobby", nullable = false)
	@Enumerated(EnumType.STRING)
	private Hobby hobby;

	@OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
	private List<InventoryItem> inventoryItems = new ArrayList<>();

	public Inventory(
		final Long memberId,
		final Hobby hobby
	) {
		this.memberId = Objects.requireNonNull(memberId);
		this.hobby = Objects.requireNonNull(hobby);
	}

	public void addInventoryItem(final InventoryItem inventoryItem) {
		inventoryItems.add(inventoryItem);
		inventoryItem.changeInventory(this);
	}

	public void removeInventoryItems() {
		this.inventoryItems.clear();
	}
}
