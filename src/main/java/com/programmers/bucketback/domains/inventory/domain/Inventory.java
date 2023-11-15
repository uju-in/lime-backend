package com.programmers.bucketback.domains.inventory.domain;

import java.util.ArrayList;
import java.util.List;

import com.programmers.bucketback.domains.common.BaseEntity;
import com.programmers.bucketback.domains.common.Hobby;

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
import jakarta.validation.constraints.NotNull;
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

	@NotNull
	@Column(name = "member_id")
	private Long memberId;

	@NotNull
	@Column(name = "hobby")
	@Enumerated(EnumType.STRING)
	private Hobby hobby;

	@OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL)
	private List<InventoryItem> inventoryItems = new ArrayList<>();

	public Inventory(
		@NotNull final Long memberId,
		@NotNull final Hobby hobby
	) {
		this.memberId = memberId;
		this.hobby = hobby;
	}

	public void addInventoryItem(final InventoryItem inventoryItem) {
		inventoryItems.add(inventoryItem);
		inventoryItem.changeInventory(this);
	}

	public void removeInventoryItems() {
		this.inventoryItems.clear();
	}
}
