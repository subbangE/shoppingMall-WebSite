// function addOption() {
//     const optionsContainer = document.getElementById('optionsContainer');
//     const optionIndex = optionsContainer.childElementCount;
//
//     const optionGroup = document.createElement('div');
//     optionGroup.classList.add('option-group');
//     optionGroup.setAttribute('id', `optionGroup${optionIndex}`);
//
//     const optionLabel = document.createElement('label');
//     optionLabel.innerText = `옵션 ${optionIndex + 1}:`;
//     optionGroup.appendChild(optionLabel);
//
//     const optionNameInput = document.createElement('input');
//     optionNameInput.type = 'text';
//     optionNameInput.name = `options[${optionIndex}].optionName`;
//     optionNameInput.placeholder = '옵션 이름';
//     optionNameInput.required = true;
//     optionGroup.appendChild(optionNameInput);
//
//     const optionValueLabel = document.createElement('label');
//     optionValueLabel.innerText = ' 옵션 값:';
//     optionGroup.appendChild(optionValueLabel);
//
//     const optionValueInput = document.createElement('input');
//     optionValueInput.type = 'text';
//     optionValueInput.name = `options[${optionIndex}].optionValue`;
//     optionValueInput.placeholder = '옵션 값 (콤마로 구분)';
//     optionValueInput.required = true;
//     optionGroup.appendChild(optionValueInput);
//
//     const optionCountLabel = document.createElement('label');
//     optionCountLabel.innerText = ' 옵션 수량:';
//     optionGroup.appendChild(optionCountLabel);
//
//     const optionCountInput = document.createElement('input');
//     optionCountInput.type = 'text';
//     optionCountInput.name = `options[${optionIndex}].optionCount`;
//     optionCountInput.placeholder = '옵션 수량';
//     optionCountInput.required = true;
//     optionGroup.appendChild(optionCountInput);
//
//     // const optionCountLabel = document.createElement('label');
//     // optionCountLabel.innerText = ' 옵션 수량:';
//     // optionGroup.appendChild(optionCountLabel);
//     //
//     // const optionCountInput = document.createElement('input');
//     // optionCountInput.type = 'number';
//     // optionCountInput.name = `options[${optionIndex}].optionCount`;
//     // optionCountInput.placeholder = '옵션 수량';
//     // optionCountInput.required = true;
//     // optionGroup.appendChild(optionCountInput);
//
//     const deleteButton = document.createElement('button');
//     deleteButton.type = 'button';
//     deleteButton.classList.add('delete-btn');
//     deleteButton.innerText = '삭제';
//     deleteButton.onclick = function() { removeOption(optionGroup.id) };
//     optionGroup.appendChild(deleteButton);
//
//     optionsContainer.appendChild(optionGroup);
// }
//
// function removeOption(optionId) {
//     const optionGroup = document.getElementById(optionId);
//     optionGroup.remove();
//     reindexOptions();
// }
//
// function reindexOptions() {
//     const optionsContainer = document.getElementById('optionsContainer');
//     const optionGroups = optionsContainer.getElementsByClassName('option-group');
//     for (let i = 0; i < optionGroups.length; i++) {
//         const optionGroup = optionGroups[i];
//         optionGroup.setAttribute('id', `optionGroup${i}`);
//         const labels = optionGroup.getElementsByTagName('label');
//         labels[0].innerText = `옵션 ${i + 1}:`;
//         const inputs = optionGroup.getElementsByTagName('input');
//         inputs[0].name = `options[${i}].optionName`;
//         inputs[1].name = `options[${i}].optionValue`;
//         inputs[2].name = `options[${i}].optionCount`;
//     }
// }
