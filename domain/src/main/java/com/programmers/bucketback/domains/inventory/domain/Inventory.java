package com.programmers.bucketback.domains.inventory.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.programmers.bucketback.Hobby;
import com.programmers.bucketback.domains.BaseEntity;

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

	@OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
	private final List<InventoryItem> inventoryItems = new ArrayList<>();
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "member_id")
	private Long memberId;
	@Column(name = "hobby")
	@Enumerated(EnumType.STRING)
	private Hobby hobby;

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
